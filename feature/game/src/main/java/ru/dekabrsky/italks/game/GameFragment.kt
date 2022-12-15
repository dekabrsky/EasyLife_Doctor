package main.java.ru.dekabrsky.italks.game

import android.content.Intent
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.game.NoWorkGameActivity
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class GameFragment : BasicFragment(), GameView {

    override val layoutRes = R.layout.basic_fragment_flow

    @InjectPresenter
    lateinit var presenter: GamePresenter

    @ProvidePresenter
    fun providePresenter(): GamePresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(GamePresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setActivity() {
        val intent = Intent(context, NoWorkGameActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}