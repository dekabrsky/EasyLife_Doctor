package ru.dekabrsky.scenarios.presentation.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.scenarios.presentation.mapper.ScenariosUiMapper
import ru.dekabrsky.scenarios.presentation.model.PatientsCodesScreenArgs
import ru.dekabrsky.scenarios.presentation.view.PatientsListView
import javax.inject.Inject

class PatientsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uiMapper: ScenariosUiMapper,
    private val interactor: DoctorPatientsInteractorImpl
) : BasicPresenter<PatientsListView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.getPatients()
            .subscribeOn(RxSchedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::dispatchLoading, viewState::showError)
            .addFullLifeCycle()
    }

    private fun dispatchLoading(items: List<ContactEntity>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.setItems(items)
        }
    }

    fun onItemClick(model: ContactEntity) {
        //router.navigateTo(Flows.Patients.SCREEN_PATIENT_DETAILS, model)
        router.startFlow(Flows.Notifications.name, NotificationsFlowArgs(Scopes.SCOPE_FLOW_PATIENTS, model.id, model.name))
    }

    fun onInviteClick() {
        router.navigateTo(Flows.Patients.SCREEN_INVITE_PATIENT)
    }
}