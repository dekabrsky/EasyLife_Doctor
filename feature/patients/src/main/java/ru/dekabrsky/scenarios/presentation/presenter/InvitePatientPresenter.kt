package ru.dekabrsky.scenarios.presentation.presenter

import main.utils.isBlankOrEmpty
import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.scenarios.presentation.model.PatientInvitationUiModel
import ru.dekabrsky.scenarios.presentation.model.PatientsCodesScreenArgs
import ru.dekabrsky.scenarios.presentation.view.InvitePatientView
import javax.inject.Inject

class InvitePatientPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: DoctorPatientsInteractorImpl
): BasicPresenter<InvitePatientView>(router) {

    private val invitation: PatientInvitationUiModel = PatientInvitationUiModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
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

        interactor.generateCode(invitation.patientName, invitation.older15, invitation.patientName, invitation.parentId)
            .observeOn(RxSchedulers.main())
            .withLoadingView(viewState)
            .subscribe(::showAddPatientsResult, viewState::showError)
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
}