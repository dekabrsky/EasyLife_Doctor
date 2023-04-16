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
import ru.dekabrsky.italks.game.databinding.GardenFragmentBinding
import ru.dekabrsky.italks.game.databinding.MainRoomLayoutBinding
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.presenter.GardenPresenter
import ru.dekabrsky.italks.game.view.presenter.MainRoomPresenter
import ru.dekabrsky.italks.game.view.utils.GameAnimationUtils.setOnClickListenerWithAnimation
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class GardenFragment : BasicFragment(), GardenView {

    override val layoutRes = R.layout.garden_fragment

    private val binding by viewBinding(GardenFragmentBinding::bind)

    @InjectPresenter
    lateinit var presenter: GardenPresenter

    @ProvidePresenter
    fun providePresenter(): GardenPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(GardenPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            binding.house.setOnClickListenerWithAnimation(it) { presenter.goToHouse() }
            binding.football.setOnClickListenerWithAnimation(it) {}
            binding.tree.setOnClickListenerWithAnimation(it) {}
            binding.barbecue.setOnClickListenerWithAnimation(it) {}
            binding.bird.setOnClickListenerWithAnimation(it) { presenter.startFlappyBird() }
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun startGameActivity() {
        val intent = Intent(context, GameActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() = GardenFragment()
    }

}