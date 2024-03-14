package ru.dekabrsky.italks.game.avatarCustomization.view.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.italks.game.avatarCustomization.view.presenter.AvatarCustomizationPresenter
import ru.dekabrsky.italks.game.databinding.FragmentAvatarCustomizationBinding
import ru.dekabrsky.italks.game.view.utils.GameAnimationUtils.setOnClickListenerWithAnimation
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class AvatarCustomizationFragment: BasicFragment(), AvatarCustomizationView {

    override val layoutRes = R.layout.fragment_avatar_customization

    private val binding by viewBinding(FragmentAvatarCustomizationBinding::bind)

    @InjectPresenter
    lateinit var presenter: AvatarCustomizationPresenter

    @ProvidePresenter
    fun providePresenter(): AvatarCustomizationPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(AvatarCustomizationPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener { presenter.onBackPressed() }

        context?.let { context ->
            binding.hatBox.setOnClickListenerWithAnimation(context) { presenter.onHatClicked() }
            binding.glassesBox.setOnClickListenerWithAnimation(context) { presenter.onGlassesClicked() }
            binding.tieBox.setOnClickListenerWithAnimation(context) { presenter.onTieClicked() }
        }
    }

    override fun updateSettings(avatarSettings: CatWidgetSettings) {
        binding.avatar.setup(avatarSettings)
        binding.hatBox.setImageResource(avatarSettings.hat.drawableRes ?: R.drawable.closed_box)
        binding.glassesBox.setImageResource(avatarSettings.glasses.drawableRes ?: R.drawable.closed_box)
        binding.tieBox.setImageResource(avatarSettings.tie.drawableRes ?: R.drawable.closed_box)
    }

    override fun showChoiceDialog(
        @StringRes title: Int,
        selectedVariantIndex: Int,
        variants: Array<String>,
        callback: (Int) -> Unit
    ) {
        var selectedVariant = selectedVariantIndex
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setSingleChoiceItems(variants, selectedVariantIndex) { _, which ->
                selectedVariant = which
            }
            .setPositiveButton("Ок") { _, _ ->
                callback.invoke(selectedVariant)
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = AvatarCustomizationFragment()
    }
}