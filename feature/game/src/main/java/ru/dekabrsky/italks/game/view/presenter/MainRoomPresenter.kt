package ru.dekabrsky.italks.game.view.presenter

import android.content.Context
import android.media.MediaPlayer
import ru.dekabrsky.feature.notifications.common.model.NotificationsFlowArgs
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.Progress
import ru.dekabrsky.italks.game.data.ProgressDb
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import ru.dekabrsky.italks.game.view.mapper.ItemsVisibilityMapper
import ru.dekabrsky.italks.game.view.mapper.ShelfItemsUiMapper
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

@Suppress("MagicNumber")
class MainRoomPresenter @Inject constructor(
    val router: FlowRouter,
    private val mapper: ShelfItemsUiMapper,
    private val visibilityMapper: ItemsVisibilityMapper,
    private val mediaPlayer: MediaPlayer,
    private val gameFlowCache: GameFlowCache
) : BasicPresenter<MainRoomView>(router) {

    var level = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setShelfItems(mapper.map(level))
        viewState.updateItemsVisibility(level, visibilityMapper.map(level))
        viewState.setupAvatar(router)
        observeMusicState()
    }

    override fun attachView(view: MainRoomView) {
        super.attachView(view)
        gameFlowCache.isMusicOnSubject.value?.let { updateMusicState(it) }
    }

    private fun observeMusicState() {
        gameFlowCache.isMusicOnSubject
            .observeOn(RxSchedulers.main())
            .subscribe(::updateMusicState)
            .addFullLifeCycle()
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

    fun saveProgress(context: Context){
        val item = Progress(null,
            "Default",
            0
        )
        Thread{
            ProgressDb.getDb(context).getDao().insertProgress(item)
        }.start()
    }

    fun onSpeakerClick() {
        gameFlowCache.isMusicOnSubject.onNext(gameFlowCache.isMusicOnSubject.value?.not() ?: false)
    }

    private fun updateMusicState(isMusicOn: Boolean) {
        viewState.setMusicIsOn(isMusicOn)
        if (isMusicOn) {
            mediaPlayer.start()
        } else {
            mediaPlayer.pause()
        }
    }

    private fun turnOffMusic() = gameFlowCache.isMusicOnSubject.onNext(false)

    private fun turnOnMusic() = gameFlowCache.isMusicOnSubject.onNext(true)

    fun onClockClick() {
        updateMusicState(false)
        // переход не работает, надо починить
    // router.startFlow(Flows.Notifications.name, NotificationsFlowArgs(Scopes.SCOPE_FLOW_GAME))
    }

    override fun onBackPressed() {
        updateMusicState(false)
        super.onBackPressed()
    }
}