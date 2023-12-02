package ru.dekabrsky.italks.game.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import gree.uniq.minigameleaves.AndroidLauncher
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.GameActivity
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.model.ProgressDb
import ru.dekabrsky.italks.game.databinding.GardenFragmentBinding
import ru.dekabrsky.italks.game.view.GardenView
import ru.dekabrsky.italks.game.view.presenter.GardenPresenter
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            binding.house.setOnClickListenerWithAnimation(it) { presenter.goToHouse() }
            binding.football.setOnClickListenerWithAnimation(it) { presenter.goToFootball() }
            binding.footballGamePad.setOnClickListenerWithAnimation(it) { presenter.goToFootball() }
            binding.tree.setOnClickListenerWithAnimation(it) { presenter.goToLeaves() }
            binding.treeGamePad.setOnClickListenerWithAnimation(it) { presenter.goToLeaves() }
            binding.barbecueGamePad.setOnClickListenerWithAnimation(it) { presenter.goToFifteen() }
            binding.barbecue.setOnClickListenerWithAnimation(it) { presenter.goToFifteen() }
            binding.bird.setOnClickListenerWithAnimation(it) { presenter.startFlappyBird() }
            binding.birdGamePad.setOnClickListenerWithAnimation(it) { presenter.startFlappyBird() }
        }
        val db = ProgressDb.getDb(requireContext())
        db.getDao().getCount().asLiveData().observe(viewLifecycleOwner){ list->
            val stringScore = list.toString()
            binding.scoreLayout.scoreText.text = stringScore
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun startFlappyBirdActivity() {
        val intent = Intent(context, GameActivity::class.java)
        startActivity(intent)
    }

    override fun startLeavesActivity() {
        val intent = Intent(context, AndroidLauncher::class.java)
        startActivity(intent)
    }

    override fun setupAvatar(router: FlowRouter) {
        binding.scoreLayout.avatar.setup(router, R.dimen.icon_20)
    }

    companion object {
        fun newInstance() = GardenFragment()
    }

}