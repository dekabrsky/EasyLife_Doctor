package ru.dekabrsky.italks.game

import android.content.Context
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import ru.dekabrsky.italks.game.data.Progress
import ru.dekabrsky.italks.game.data.ProgressDb

class GameActivity : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(FlappyBird(), AndroidApplicationConfiguration())
    }
}