package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.scenarios.presentation.model.SelectParentArgs
import ru.dekabrsky.scenarios.presentation.view.SelectParentView
import javax.inject.Inject

class SelectParentPresenter @Inject constructor(
    private val router: FlowRouter,
    private val args: SelectParentArgs
): BasicPresenter<SelectParentView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setList(args.variants)
    }

    fun onItemClick(item: ContactEntity) {
        router.backWithResult(SELECT_PARENT_RESULT_CODE, item)
    }

    fun isItemChecked(id: Long): Boolean {
        return args.selectedParentId == id
    }

    companion object {
        val SELECT_PARENT_RESULT_CODE = this::class.hashCode()
    }
}