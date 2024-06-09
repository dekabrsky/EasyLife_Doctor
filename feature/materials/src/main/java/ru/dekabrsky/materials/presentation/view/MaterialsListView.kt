package ru.dekabrsky.materials.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel

@AddToEndSingle
interface MaterialsListView: BasicView {
    fun setMaterialsList(items: List<MaterialDetailsUiModel>)
}