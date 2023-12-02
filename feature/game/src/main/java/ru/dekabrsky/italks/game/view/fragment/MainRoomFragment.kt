package ru.dekabrsky.italks.game.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.model.ProgressDb
import ru.dekabrsky.italks.game.databinding.MainRoomLayoutBinding
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.adapter.ShelfAdapter
import ru.dekabrsky.italks.game.view.model.ItemVisibility
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel
import ru.dekabrsky.italks.game.view.presenter.MainRoomPresenter
import ru.dekabrsky.italks.game.view.utils.GameAnimationUtils.setOnClickListenerWithAnimation
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

@Suppress("MagicNumber")
class MainRoomFragment: BasicFragment(), MainRoomView {

    override val layoutRes = R.layout.main_room_layout

    private var stringScore = ""
    var level = 1

    private val binding by viewBinding(MainRoomLayoutBinding::bind)

    private val shelfAdapter by lazy { ShelfAdapter() }

    @InjectPresenter
    lateinit var presenter: MainRoomPresenter

    @ProvidePresenter
    fun providePresenter(): MainRoomPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(MainRoomPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as GameFlowFragment).setNavBarVisibility(false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            binding.avatar.setOnClickListenerWithAnimation(it) {}
            binding.door.setOnClickListenerWithAnimation(it) { presenter.onDoorClick() }
            binding.window.setOnClickListenerWithAnimation(it) { presenter.onDoorClick() }
            binding.clock.setOnClickListenerWithAnimation(it) {}
        }
        initShelf()
        binding.scrollContainer.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            binding.progressMedal.root.translationX = scrollX.toFloat()
            binding.scoreLayout.root.translationX = scrollX.toFloat()
        }
        presenter.saveProgress(requireContext())
        val db = ProgressDb.getDb(requireContext())
        db.getDao().getCount().asLiveData().observe(viewLifecycleOwner){ list->
            stringScore = list.toString()
            binding.scoreLayout.scoreText.text = stringScore
        }
        if (stringScore == "" || stringScore.toInt() >= 0 || stringScore.toInt() < 5000) {
            level = 1
            presenter.onMedalClick()
        } else if(stringScore.toInt() >= 5000 || stringScore.toInt() < 12000) {
            level = 2
            presenter.onMedalClick()
        } else if(stringScore.toInt() >= 12000 || stringScore.toInt() < 20000) {
            level = 3
            presenter.onMedalClick()
        } else if(stringScore.toInt() >= 20000) {
            level = 4
            presenter.onMedalClick()
        }
    }

    private fun initShelf() {
        binding.shelf.layoutManager = GridLayoutManager(this.context, SHELF_SPAN_COUNT)
        binding.shelf.adapter = shelfAdapter
    }

    override fun setShelfItems(list: List<ShelfItemUiModel>) {
        shelfAdapter.updateItems(list)
    }

    override fun updateItemsVisibility(level: Int, itemsVisibility: List<ItemVisibility>) {
        binding.progressMedal.medalText.text = "$level"
        itemsVisibility.forEach {
            binding.root.findViewById<View>(it.viewId).visibility =
                if (it.visible) View.VISIBLE else View.GONE
        }
    }

    override fun setupAvatar(router: FlowRouter) {
        binding.scoreLayout.avatar.setup(router, R.dimen.icon_20)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        private const val SHELF_SPAN_COUNT = 4
        fun newInstance() = MainRoomFragment()
    }

}