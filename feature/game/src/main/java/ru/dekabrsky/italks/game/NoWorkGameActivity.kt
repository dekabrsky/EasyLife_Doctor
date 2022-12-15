package ru.dekabrsky.italks.game

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class NoWorkGameActivity : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(FlappyBird(), AndroidApplicationConfiguration())
    }

    companion object {
        fun newInstance() = NoWorkGameActivity()
    }
}