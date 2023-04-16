package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.mapper.ShelfItemsUiMapper
import javax.inject.Inject

class MainRoomPresenter @Inject constructor(
    val router: FlowRouter,
    val mapper: ShelfItemsUiMapper
) : BasicPresenter<MainRoomView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setShelfItems(mapper.map())
    }

    fun onDoorClick() = router.navigateTo(Flows.Game.SCREEN_GARDEN)
}