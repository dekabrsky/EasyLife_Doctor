package ru.dekabrsky.materials.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel

@AddToEndSingle
interface MaterialDetailsView : MvpView {
    fun showMaterial(material: MaterialDetailsUiModel)
}