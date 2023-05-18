package ru.dekabrsky.dialings.presentation.presenter

import ru.dekabrsky.dialings.domain.interactor.DialingsInteractorImpl
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.dialings.presentation.mapper.DialingListUiMapper
import ru.dekabrsky.dialings.domain.interactor.ProductsInteractor
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.model.FilterScreenArgs
import ru.dekabrsky.dialings.presentation.model.FiltersResult
import ru.dekabrsky.dialings.presentation.view.DialingsListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import java.util.UUID
import javax.inject.Inject

class DialingsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uiMapper: DialingListUiMapper,
    private val interactor: DialingsInteractorImpl,
    private val productsInteractor: ProductsInteractor
) : BasicPresenter<DialingsListView>(router) {

    private var sortBy: SortVariants = SortVariants.CREATION_DATE
    private var orderBy: Direction = Direction.ASC
    private var filter: DialingStatus? = null // нет фильтра -> все

    private var cityFilters: List<String> = listOf()
    private var typeFilters: List<String> = listOf()
    private var json: String = ""

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

   fun onToDetailsClick(model: PlainProduct) {
       router.navigateTo(Flows.Events.SCREEN_DIALING_DETAILS, model)
   }

    fun onRunClick(id: Int) {
        viewState.showRunNowDialog(id)
    }

    fun runNow(id: Int) {
        interactor.startDialing(id)
            .observeOn(RxSchedulers.main())
            .subscribe(
                { changeFilter(DialingStatus.RUN) },
                {
                    changeFilter(DialingStatus.RUN) // до починки метода
                    //viewState.showError(it, ::load)
                }
            )
            .addFullLifeCycle()
    }

    private fun changeFilter(status: DialingStatus) {
//        val chipId = when (status) {
//            null -> R.id.allChip
//            DialingStatus.SCHEDULED -> R.id.scheduledChip
//            DialingStatus.RUN -> R.id.runningChip
//            DialingStatus.DONE -> R.id.doneChip
//        }
//        viewState.setFilter(chipId)
    }

    private fun dispatchLoading(items: List<PlainProduct>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.hideEmptyLayout()
            viewState.setItems(items)
        }
    }

    private fun load() {
        dispatchLoading(productsInteractor.getProducts(cityFilters, typeFilters))
    }

    fun setJson(json: String?) {
        if (this.json.isEmpty()) {
            this.json = json.orEmpty()
            productsInteractor.parseProducts(json)
        }
        load()
    }

    fun onCityFilterClick() {
        router.setResultListener(CITY_RESULT) {
            cityFilters = (it as? FiltersResult)?.selected.orEmpty()
            viewState.setCityMarkerVisibility(cityFilters.isNotEmpty())
            load()
            router.removeResultListener(CITY_RESULT)
        }

        router.navigateTo(
            Flows.Events.SCREEN_FILTER,
            FilterScreenArgs(
                resultCode = CITY_RESULT,
                name = "Город",
                values = productsInteractor.getCities(),
                selectedValues = cityFilters
            )
        )
    }

    fun onTypeFilterClick() {
        router.setResultListener(TYPE_RESULT) {
            typeFilters = (it as? FiltersResult)?.selected.orEmpty()
            viewState.setTypeMarkerVisibility(typeFilters.isNotEmpty())
            load()
            router.removeResultListener(TYPE_RESULT)
        }

        router.navigateTo(
            Flows.Events.SCREEN_FILTER,
            FilterScreenArgs(
                resultCode = TYPE_RESULT,
                name = "Тип изделия",
                values = productsInteractor.getTypes(),
                selectedValues = typeFilters
            )
        )
    }

    companion object {
        private val CITY_RESULT = UUID.randomUUID().variant()
        private val TYPE_RESULT = UUID.randomUUID().variant()
    }
}