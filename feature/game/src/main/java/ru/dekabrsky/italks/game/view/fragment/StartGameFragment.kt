package ru.dekabrsky.italks.game.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.GameActivity
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.databinding.StartGameFragmentBinding
import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.game.view.presenter.GameStartPresenter
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import toothpick.Toothpick

class StartGameFragment : BasicFragment(), GameView {

    override val layoutRes = R.layout.start_game_fragment

    private val binding by viewBinding(StartGameFragmentBinding::bind)

    @InjectPresenter
    lateinit var presenter: GameStartPresenter

    @ProvidePresenter
    fun providePresenter(): GameStartPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(GameStartPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            presenter.onGameStartClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as GameFlowFragment).setNavBarVisibility(true)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = StartGameFragment()
    }

    override fun startGameActivity() {
        val intent = Intent(context, GameActivity::class.java)
        startActivity(intent)
    }
}