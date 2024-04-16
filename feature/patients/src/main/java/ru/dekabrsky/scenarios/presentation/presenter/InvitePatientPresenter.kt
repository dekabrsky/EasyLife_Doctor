package ru.dekabrsky.scenarios.presentation.presenter

import main.utils.isBlankOrEmpty
import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.scenarios.presentation.model.PatientInvitationUiModel
import ru.dekabrsky.scenarios.presentation.model.PatientsCodesScreenArgs
import ru.dekabrsky.scenarios.presentation.model.SelectParentArgs
import ru.dekabrsky.scenarios.presentation.view.InvitePatientView
import javax.inject.Inject

class InvitePatientPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: DoctorPatientsInteractorImpl,
    private val contactsInteractor: ContactsInteractor,
    private val errorHandler: ServerErrorHandler
): BasicPresenter<InvitePatientView>(router) {

    private val invitation: PatientInvitationUiModel = PatientInvitationUiModel()
    
    private var parents: List<ContactEntity> = listOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        contactsInteractor.getParents()
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({ parents = it }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
    }

    fun onPatientNameChanged(name: String) {
        invitation.patientName = name
    }

    fun onParentNameChanged(name: String) {
        invitation.parentName = name
    }

    fun isPatientOlder15CheckedChanged(checked: Boolean) {
        invitation.older15 = checked
    }

    fun onDoneClick() {
        val errors = mutableListOf<String>().apply {
            if (invitation.patientName.isBlankOrEmpty()) add("Введите имя пациента")
            if (invitation.parentName.isBlankOrEmpty()) add("Введите имя законного представителя")
        }.joinToString("\n")

        if (errors.isEmpty().not()) {
            viewState.showToast(errors)
            return
        }

        interactor.generateCode(
            invitation.patientName,
            invitation.older15.not(),
            invitation.parentName,
            invitation.parentId
        )
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe(::showAddPatientsResult, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
    }

    private fun showAddPatientsResult(patientCodeEntity: PatientCodeEntity) {
        router.replaceScreen(
            Flows.Patients.SCREEN_PATIENTS_CODES,
            PatientsCodesScreenArgs(
                patientCode = patientCodeEntity.patientCode,
                parentCode = patientCodeEntity.parentCode
            )
        )
    }

    fun onParentsListClick() {
        router.navigateTo(Flows.Patients.SCREEN_SELECT_PARENT, SelectParentArgs(invitation.parentId, parents))
        router.setResultListener(SelectParentPresenter.SELECT_PARENT_RESULT_CODE) {
            router.removeResultListener(SelectParentPresenter.SELECT_PARENT_RESULT_CODE)
            if (it !is ContactEntity) return@setResultListener
            invitation.parentId = it.id
            invitation.parentName = it.displayName
            setParentToView()
        }
    }

    private fun setParentToView() {
        val isSelected = invitation.parentId != null
        val parentName = invitation.parentName.takeIf { isSelected }.orEmpty()
        val link = invitation.parentName.takeIf { isSelected } ?: "Выбрать существующего"
        viewState.setParentState(isSelected, parentName, link)
    }

    fun onParentCrossClick() {
        invitation.parentId = null
        invitation.patientName = ""
        setParentToView()
    }
}