package ru.dekabrsky.materials.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel
import ru.dekabrsky.materials.presentation.view.MaterialDetailsView
import javax.inject.Inject

class MaterialDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val material: MaterialDetailsUiModel
): BasicPresenter<MaterialDetailsView>(router) {
    override fun onFirstViewAttach() {
        viewState.showMaterial(material)
    }
}