package main.utils

import android.content.Context
import android.util.DisplayMetrics
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

fun dpToPx(context: Context, dp: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun pxToDp(context: Context,px: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}