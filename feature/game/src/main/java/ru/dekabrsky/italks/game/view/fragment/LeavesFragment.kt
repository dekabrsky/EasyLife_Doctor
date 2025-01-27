package ru.dekabrsky.easylife.game.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.game.R
import ru.dekabrsky.easylife.game.databinding.FragmentLeavesBinding
import ru.dekabrsky.easylife.game.view.LeavesView
import ru.dekabrsky.easylife.game.view.model.Leaves
import ru.dekabrsky.easylife.game.view.presenter.LeavesPresenter
import ru.dekabrsky.easylife.scopes.Scopes
import toothpick.Toothpick

@Suppress("NAME_SHADOWING", "DEPRECATION")
class LeavesFragment: BasicFragment(), LeavesView {
    private lateinit var leavesView: Leaves

    override val layoutRes = R.layout.fragment_leaves

    @InjectPresenter
    lateinit var presenter: LeavesPresenter

    private val viewBinding by viewBinding(FragmentLeavesBinding::bind)

    @ProvidePresenter
    fun providePresenter(): LeavesPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(LeavesPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leaves, container, false)

        leavesView = Leaves(requireContext())

        startGame()

        val container = view.findViewById<ViewGroup>(R.id.game_container)
        container.addView(leavesView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.removeButton.setOnClickListener { presenter.restart() }
        viewBinding.exitGame.setOnClickListener {
            presenter.exitGame()
            progress()
        }
    }

    private fun progress(){
        val score = leavesView.saveScore()
        presenter.saveProgress(score, true)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
        progress()
    }

    private fun startGame() {
        leavesView.startGame()
    }

    companion object {
        fun newInstance() = LeavesFragment()
    }
}