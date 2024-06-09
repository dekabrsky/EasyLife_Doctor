package ru.dekabrsky.easylife.game.view.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import ru.dekabrsky.easylife.game.R

object GameAnimationUtils {
    fun View.setOnClickListenerWithAnimation(context: Context, onClick: () -> Unit) {
        this.setOnClickListener {
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.default_on_click_animation))
            this.playSoundEffect(android.view.SoundEffectConstants.CLICK)
            onClick.invoke()
        }
    }
}