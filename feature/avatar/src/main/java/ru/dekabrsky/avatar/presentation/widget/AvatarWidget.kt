package ru.dekabrsky.avatar.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import main.utils.orZero
import ru.dekabrsky.avatar.R
import ru.dekabrsky.avatar.databinding.WidgetAvatarBinding
import ru.dekabrsky.avatar.domain.AvatarType
import ru.dekabrsky.avatar.presentation.utils.AvatarUriProvider
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import toothpick.Toothpick
import javax.inject.Inject

private const val CORNERS_RADIUS = 16

class AvatarWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var sharedPreferencesProvider: SharedPreferencesProvider

    @Inject
    lateinit var avatarUriProvider: AvatarUriProvider

    private var router: FlowRouter? = null
    private var sizeRes: Int = R.dimen.icon_48
    private val size get() = resources?.getDimension(sizeRes)?.toInt().orZero()

    private val viewBinding = WidgetAvatarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        Toothpick.openScope(Scopes.SCOPE_APP).inject(this)
        viewBinding.avatar.setOnClickListener { onClick() }
    }

    fun setup(router: FlowRouter? = null, sizeRes: Int = R.dimen.icon_48) {
        this.router = router
        this.sizeRes = sizeRes
        AvatarType.getByValueOrNull(sharedPreferencesProvider.gameAvatar.get())?.let { avatarType ->
            setAvatar(avatarType)
        } ?: setDefaultImage()
    }

    fun setAvatar(avatarType: AvatarType) =
        Glide
            .with(viewBinding.avatar)
            .load(avatarUriProvider.provideUri(avatarType))
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(CORNERS_RADIUS)))
            .override(size, size)
            .into(viewBinding.avatar)

    private fun setDefaultImage() =
        Glide
            .with(viewBinding.avatar)
            .load(R.drawable.ic_round_group_24)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(CORNERS_RADIUS)))
            .override(size, size)
            .into(viewBinding.avatar)

    private fun onClick() {
        router?.startFlow(Flows.Avatar.name)
    }
}