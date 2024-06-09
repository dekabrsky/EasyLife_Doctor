package ru.dekabrsky.materials.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.materials.presentation.view.MaterialsFlowView
import javax.inject.Inject

class MaterialsFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<MaterialsFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Materials.SCREEN_MATERIALS_LIST)
    }
}