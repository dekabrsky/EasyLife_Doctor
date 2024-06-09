package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.basic.resources.ResourceProvider
import ru.dekabrsky.easylife.basic.rx.withCustomLoadingView
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.scenarios.presentation.view.PatientsListView
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class PatientsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: DoctorPatientsInteractorImpl,
    private val loginDataCache: LoginDataCache,
    private val resourceProvider: ResourceProvider,
    private val errorHandler: ServerErrorHandler
) : BasicPresenter<PatientsListView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.getPatients()
            .subscribeOnIo()
            .withCustomLoadingView(viewState::setLoadingViewVisibility)
            .subscribe(::dispatchLoading) { errorHandler.onError(it, viewState) }
            .addFullLifeCycle()
    }

    private fun dispatchLoading(items: List<ContactEntity>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.setItems(items)
            checkNewRegistration(items)
            checkMedicinesDiff(items)
        }
    }

    private fun checkNewRegistration(items: List<ContactEntity>) {
        loginDataCache.registeredPatientId?.let { id ->

            val newPatient = items.find { it.id == id.toLongOrNull() }?.displayName.orEmpty()
            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = resourceProvider.getString(R.string.patient_new_registration_title),
                    subtitle = resourceProvider.getString(R.string.patient_new_registration_message, newPatient),
                    mode = BottomSheetMode.LK
                )
            )

            loginDataCache.registeredPatientId = null
        }
    }

    private fun checkMedicinesDiff(items: List<ContactEntity>) {
        loginDataCache.patientMedicinesDiff?.let { patientDiff ->

            val patient = items.find { it.id == patientDiff.patientId.toLongOrNull() }

            router.navigateTo(
                Flows.Common.SCREEN_BOTTOM_INFO,
                BottomSheetScreenArgs(
                    title = resourceProvider.getString(
                        R.string.patient_update_notifications,
                        patient?.displayName.orEmpty()
                    ),
                    subtitle = patientDiff.diff,
                    mode = BottomSheetMode.LK,
                    buttonState = ButtonState(resourceProvider.getString(R.string.look)) {
                        patient?.let { onItemClick(patient) }
                    }
                )
            )

            loginDataCache.patientMedicinesDiff = null
        }
    }

    fun onItemClick(model: ContactEntity) {
        //router.navigateTo(Flows.Patients.SCREEN_PATIENT_DETAILS, model)
        router.startFlow(
            Flows.Notifications.name,
            NotificationsFlowArgs(Scopes.SCOPE_FLOW_PATIENTS, model.id, model.displayName)
        )
    }

    fun onInviteClick() {
        router.navigateTo(Flows.Patients.SCREEN_INVITE_PATIENT)
    }
}