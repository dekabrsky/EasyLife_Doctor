package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.mapper.ItemsVisibilityMapper
import ru.dekabrsky.italks.game.view.mapper.ShelfItemsUiMapper
import javax.inject.Inject

@Suppress("MagicNumber")
class MainRoomPresenter @Inject constructor(
    val router: FlowRouter,
    private val mapper: ShelfItemsUiMapper,
    private val visibilityMapper: ItemsVisibilityMapper
) : BasicPresenter<MainRoomView>(router) {

    var level = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setShelfItems(mapper.map(level))
        viewState.updateItemsVisibility(level, visibilityMapper.map(level))
    }

    fun onDoorClick() = router.navigateTo(Flows.Game.SCREEN_GARDEN)
    fun onMedalClick() {
        level = (level + 1) % 5
        if (level == 0) level = 1
        viewState.updateItemsVisibility(level, visibilityMapper.map(level))
        viewState.setShelfItems(mapper.map(level))
    }
}