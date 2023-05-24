package ru.dekabrsky.simpleBottomsheet.view.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import ru.dekabrsky.simpleBottomsheet.R
import java.io.Serializable

class BottomSheetScreenArgs(
    val title: String,
    val subtitle: String,
    val mode: BottomSheetMode = BottomSheetMode.LK,
    @DrawableRes val icon: Int? = null ,
    val buttonState: ButtonState? = null
): Serializable

enum class BottomSheetMode(
    @ColorRes val bgColor: Int,
    @ColorRes val textColor: Int
) {
    LK(bgColor = R.color.white, textColor = R.color.black),
    GAME(bgColor = R.color.cyan_main, textColor = R.color.white)
}

class ButtonState(
    val text: String,
    val action: () -> Unit
)