package ru.dekabrsky.easylife.game.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.game.R
import ru.dekabrsky.easylife.game.databinding.StartGameFragmentBinding
import ru.dekabrsky.easylife.game.view.GameView
import ru.dekabrsky.easylife.game.view.presenter.GameStartPresenter
import ru.dekabrsky.easylife.scopes.Scopes
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

    override fun setupAvatar(router: FlowRouter) {
        binding.avatar.setup(router)
    }

    companion object {
        fun newInstance() = StartGameFragment()
    }
}