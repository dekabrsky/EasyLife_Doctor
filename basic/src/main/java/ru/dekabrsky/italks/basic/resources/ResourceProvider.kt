package ru.dekabrsky.easylife.basic.resources

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import javax.inject.Inject

@Suppress("SpreadOperator", "ArrayPrimitive")
class ResourceProvider @Inject constructor(private val context: Context) {
    fun getString(resId: Int): String = context.getString(resId)
    fun getString(resId: Int, vararg arg: Any): String = context.getString(resId, *arg)
    fun getText(resId: Int): CharSequence = context.getText(resId)
    fun getQuantityString(resId: Int, quantity: Int, vararg arg: Any = arrayOf(quantity)): String =
        context.resources.getQuantityString(resId, quantity, *arg)

    fun getColor(colorRes: Int): Int = ContextCompat.getColor(context, colorRes)
    fun getDimension(dimenRes: Int): Float = context.resources.getDimension(dimenRes)
    fun getStringArray(resId: Int): Array<String> = context.resources.getStringArray(resId)
    fun getIntArray(resId: Int): Array<Int> = context.resources.getIntArray(resId).toTypedArray()
    fun getInt(resId: Int): Int = context.resources.getInteger(resId)
    fun getDimensionPixelSize(dimenRes: Int) = context.resources.getDimensionPixelSize(dimenRes)
    fun getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)
    fun getFont(resId: Int): Typeface? = ResourcesCompat.getFont(context, resId)
}