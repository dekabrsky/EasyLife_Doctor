package ru.dekabrsky.italks.minigamestwolast

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    fun dropIn(view: View) {
        val counter = view as ImageView
        val txt = findViewById<TextView>(R.id.winner1)
        val bt2 = findViewById<ImageView>(R.id.button2)
        val tappedcounter = counter.tag.toString().toInt()
        if (gameState[tappedcounter] == 2 && gameIsActive) {
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.basket)
                activePlayer = 0
                count++
                gameState[tappedcounter] = 1
            } else {
                counter.setImageResource(R.drawable.img)
                activePlayer = 1
                count++
                gameState[tappedcounter] = 0
            }
            counter.translationY = -1000f
            counter.animate().translationYBy(1000f).rotationY(1800f).duration = 1000
            for (winningposition in winningPositions) {
                if (gameState[winningposition[0]] == gameState[winningposition[1]] && gameState[winningposition[1]] == gameState[winningposition[2]] && gameState[winningposition[0]] != 2
                ) {
                    if (gameState[winningposition[0]] == 0) {
                        txt.text = "Вы проиграли"
                        txt.setTextColor(Color.RED) }
                    else if (gameState[winningposition[0]] == 1
                    ) { txt.text = "Вы победили!"
                    txt.setTextColor(Color.GREEN) }
                    bt2.visibility = View.VISIBLE
                    gameIsActive = false
                }
            }
        }
        if (gameIsActive && count == 9) {
            txt.text = "DRAW"
            bt2.visibility = View.VISIBLE
            gameIsActive = false
        }
    }

    fun playAgain(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        val bt2 = findViewById<ImageView>(R.id.button2)
        val txt = findViewById<TextView>(R.id.winner1)
        val gridLayout =
            findViewById<GridLayout>(R.id.gridLayout)
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        bt2.visibility = View.INVISIBLE
        txt.visibility = View.INVISIBLE
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
    }
}