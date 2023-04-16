package ru.dekabrsky.italks.game.view.fragment

import android.R.attr.data
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.databinding.MainRoomLayoutBinding
import ru.dekabrsky.italks.game.view.MainRoomView
import ru.dekabrsky.italks.game.view.adapter.ShelfAdapter
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel
import ru.dekabrsky.italks.game.view.presenter.MainRoomPresenter
import ru.dekabrsky.italks.game.view.utils.GameAnimationUtils.setOnClickListenerWithAnimation
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick


class MainRoomFragment: BasicFragment(), MainRoomView {

    override val layoutRes = R.layout.main_room_layout

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            binding.avatar.setOnClickListenerWithAnimation(it) {}
            binding.door.setOnClickListenerWithAnimation(it) { presenter.onDoorClick() }
            binding.window.setOnClickListenerWithAnimation(it) { presenter.onDoorClick() }
            binding.clock.setOnClickListenerWithAnimation(it) {}
        }
        initShelf()
    }

    private fun initShelf() {
        binding.shelf.layoutManager = GridLayoutManager(this.context, SHELF_SPAN_COUNT)
        binding.shelf.adapter = shelfAdapter
    }

    override fun setShelfItems(list: List<ShelfItemUiModel>) {
        shelfAdapter.updateItems(list)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        private const val SHELF_SPAN_COUNT = 4
        fun newInstance() = MainRoomFragment()
    }

}