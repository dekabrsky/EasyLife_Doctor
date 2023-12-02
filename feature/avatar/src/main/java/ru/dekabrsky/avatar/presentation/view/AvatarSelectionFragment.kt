package ru.dekabrsky.avatar.presentation.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import main.utils.setBoolVisibility
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.avatar.R
import ru.dekabrsky.avatar.databinding.FmtChooseAvatarBinding
import ru.dekabrsky.avatar.domain.AvatarType
import ru.dekabrsky.avatar.presentation.adapter.AvatarsAdapter
import ru.dekabrsky.avatar.presentation.presenter.AvatarSelectionPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class AvatarSelectionFragment: BasicFragment(), AvatarSelectionView {

    override val layoutRes = R.layout.fmt_choose_avatar

    private val binding by viewBinding(FmtChooseAvatarBinding::bind)

    private val adapter by lazy {
        AvatarsAdapter(
            onItemClick = presenter::onAvatarClick,
            isItemSelected = presenter::isAvatarSelected,
            provideUri = presenter::provideUri
        )
    }

    @InjectPresenter
    lateinit var presenter: AvatarSelectionPresenter

    @ProvidePresenter
    fun providePresenter(): AvatarSelectionPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_AVATAR, scopeName)
            .getInstance(AvatarSelectionPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.avatarsList.adapter = adapter
        binding.avatarsList.layoutManager = GridLayoutManager(context, 2)
        binding.saveBtn.setOnClickListener { presenter.onSaveBtnClicked() }
        binding.backBtn.setOnClickListener { presenter.onBackPressed() }
    }

    override fun setAvatars(avatars: List<AvatarType>) {
        adapter.updateItems(avatars)
    }

    override fun setButtonVisibility(isVisible: Boolean) {
        binding.saveBtn.setBoolVisibility(isVisible)
    }

    override fun refreshAvatars() {
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = AvatarSelectionFragment()
    }
}