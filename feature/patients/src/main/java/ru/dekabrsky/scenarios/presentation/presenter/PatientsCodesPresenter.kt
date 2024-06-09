package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.scenarios.presentation.view.PatientsCodesView
import javax.inject.Inject

class PatientsCodesPresenter @Inject constructor(private val router: FlowRouter) :
    BasicPresenter<PatientsCodesView>(router)