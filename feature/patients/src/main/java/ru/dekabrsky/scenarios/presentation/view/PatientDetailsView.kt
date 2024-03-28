package ru.dekabrsky.scenarios.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel

@AddToEndSingle
interface PatientDetailsView : BasicView {
    fun setupTakingMedications(dialings: List<TakingMedicationsUiModel>)
    fun setMainData(model: ContactEntity)
}