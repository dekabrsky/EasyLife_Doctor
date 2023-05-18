package ru.dekabrsky.dialings.presentation.presenter

import ru.dekabrsky.dialings.presentation.model.FilterScreenArgs
import ru.dekabrsky.dialings.presentation.model.FiltersResult
import ru.dekabrsky.dialings.presentation.view.FilterListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class FiltersListPresenter @Inject constructor(
    private val screenArgs: FilterScreenArgs,
    private val router: FlowRouter
): BasicPresenter<FilterListView>() {

    private var selectedItems = mutableListOf<String>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setList(screenArgs.values)
        selectedItems = screenArgs.selectedValues.toMutableList()
    }

    fun isChecked(item: String): Boolean {
        return selectedItems.contains(item)
    }

    fun onItemCheckedChanged(item: String, isChecked: Boolean) {
        if (isChecked) {
            if (selectedItems.contains(item).not()) selectedItems.add(item)
        } else {
            if (selectedItems.contains(item)) selectedItems.remove(item)
        }
    }

    fun onSaveClick() {
        router.backWithResult(screenArgs.resultCode, FiltersResult(selectedItems))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.back()
    }
}