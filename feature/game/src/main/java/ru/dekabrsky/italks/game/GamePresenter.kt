package main.java.ru.dekabrsky.italks.game

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class GamePresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<GameView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setActivity()
    }
}