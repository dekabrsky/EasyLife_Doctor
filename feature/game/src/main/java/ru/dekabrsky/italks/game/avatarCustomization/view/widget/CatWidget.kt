package ru.dekabrsky.italks.game.avatarCustomization.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ru.dekabrsky.italks.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.italks.game.databinding.WidgetCatBinding

class CatWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewBinding = WidgetCatBinding.inflate(LayoutInflater.from(context), this, true)

    fun setup(settings: CatWidgetSettings) {
        settings.hat.drawableRes?.let { viewBinding.hat.setImageResource(it) }
            ?: let { viewBinding.hat.setImageDrawable(null) }
        settings.glasses.drawableRes?.let { viewBinding.glasses.setImageResource(it) }
            ?: let { viewBinding.glasses.setImageDrawable(null) }
        settings.tie.drawableRes?.let { viewBinding.tie.setImageResource(it) }
            ?: let { viewBinding.tie.setImageDrawable(null) }
    }
}