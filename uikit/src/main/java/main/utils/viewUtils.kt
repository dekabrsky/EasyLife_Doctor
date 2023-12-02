package main.utils

import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton

fun View.setBoolVisibility(isVisible: Boolean, withGone: Boolean = true) = run {
    when {
        isVisible -> visible()
        withGone -> gone()
        else -> invisible()
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun CompoundButton.setIsCheckedWithoutEffects(isChecked: Boolean, listener: (Boolean) -> Unit) {
    this.setOnCheckedChangeListener(null)
    this.isChecked = isChecked
    this.setOnCheckedChangeListener { _, isChecked -> listener.invoke(isChecked) }
}