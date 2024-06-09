package ru.dekabrsky.dialings.presentation.presenter

import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.domain.interactor.DialingsInteractorImpl
import ru.dekabrsky.dialings.presentation.mapper.DialingListUiMapper
import ru.dekabrsky.dialings.presentation.view.DialingsListView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.Direction
import ru.dekabrsky.easylife.basic.network.utils.SortVariants
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import javax.inject.Inject

class DialingsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uiMapper: DialingListUiMapper,
    private val interactor: DialingsInteractorImpl
) : BasicPresenter<DialingsListView>(router) {

    private var sortBy: SortVariants = SortVariants.CREATION_DATE
    private var orderBy: Direction = Direction.ASC
    private var filter: DialingStatus? = null // нет фильтра -> все

    private fun load() {
//        interactor.getDialings(orderBy, sortBy, filter)
//            .observeOn(RxSchedulers.main())
//            .map { it.map { entity -> uiMapper.map(entity) } }
//            .subscribe(
//                { dispatchLoading(it) },
//                {
//                    viewState.showError(it, ::load)
//                    viewState.showNoConnectionDialog()
//                    viewState.showEmptyLayout()
//                }
//            )
//            .addFullLifeCycle()

        viewState.showEmptyLayout()
    }

    fun loadSortByName() {
        sortBy = SortVariants.NAME
        orderBy = Direction.ASC
        load()
    }

    fun loadSortByDateAsc() {
        sortBy = SortVariants.CREATION_DATE
        orderBy = Direction.ASC
        load()
    }

    fun loadSortByDateDesc() {
        sortBy = SortVariants.CREATION_DATE
        orderBy = Direction.ASC
        load()
    }

   fun onToDetailsClick(model: DialingUiModel) {
       router.navigateTo(Flows.Events.SCREEN_DIALING_DETAILS, model.id)
   }

    fun onRunClick(id: Int) {
        viewState.showRunNowDialog(id)
    }

    fun runNow(id: Int) {
        interactor.startDialing(id)
            .subscribeOnIo()
            .subscribe(
                { changeFilter(DialingStatus.RUN) },
                {
                    changeFilter(DialingStatus.RUN) // до починки метода
                    //viewState.showError(it, ::load)
                }
            )
            .addFullLifeCycle()
    }

    fun onFilterChanged(checkedId: Int) {
        viewState.setFilter(checkedId)
        when (checkedId) {
            R.id.allChip -> filter = null
            R.id.scheduledChip -> filter = DialingStatus.SCHEDULED
            R.id.runningChip -> filter = DialingStatus.RUN
            R.id.doneChip -> filter = DialingStatus.DONE
        }
        load()
    }

    private fun changeFilter(status: DialingStatus) {
        val chipId = when (status) {
            null -> R.id.allChip
            DialingStatus.SCHEDULED -> R.id.scheduledChip
            DialingStatus.RUN -> R.id.runningChip
            DialingStatus.DONE -> R.id.doneChip
        }
        viewState.setFilter(chipId)
    }

    private fun dispatchLoading(items: List<DialingUiModel>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.hideEmptyLayout()
            viewState.setItems(items)
        }
    }


}