package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.mapper.ItemsVisibilityMapper
import ru.dekabrsky.italks.game.view.mapper.ShelfItemsUiMapper
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
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

    fun onDoorClick() = router.navigateTo(
        Flows.Common.SCREEN_BOTTOM_INFO,
        BottomSheetScreenArgs(
            title = "Хочешь выйти во двор?",
            subtitle = "Играй в мини-игры и получай за них очки прогресса\n" +
                    "По мере набора очков тебе будут открываться новые элементы интерьера!",
            mode = BottomSheetMode.GAME,
            icon = R.drawable.coin,
            buttonState = ButtonState("Вперед!") { router.navigateTo(Flows.Game.SCREEN_GARDEN) }
        )
    )
    fun onMedalClick() {
    //    this.level = (this.level + 1) % 5
        if (this.level == 0) this.level = 1
        viewState.updateItemsVisibility(this.level, visibilityMapper.map(this.level))
        viewState.setShelfItems(mapper.map(this.level))
    }
}