package ru.dekabrsky.easylife.game.view.model

import android.graphics.Rect

@Suppress("MagicNumber")
class Leaf(screenWidth: Int, screenHeight: Int) {

    private val speed = 10
    private val size = 100

    var x: Int = (0..screenWidth - size).random()
    var y: Int = -size

    var dissonans = y > screenHeight

    val rect = Rect(x, y, x + size, y + size)

    fun update() {
        y += speed
        rect.set(x, y, x + size, y + size)
    }

    fun isOutOfBounds(): Boolean {
        return dissonans
    }

    fun isCaught(boxRect: Rect): Boolean {
        return rect.intersect(boxRect)
    }
}