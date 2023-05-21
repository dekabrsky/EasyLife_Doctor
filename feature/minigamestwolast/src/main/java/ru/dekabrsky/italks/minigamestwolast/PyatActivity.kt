package ru.dekabrsky.italks.minigamestwolast

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import ru.dekabrsky.italks.minigamestwolast.databinding.ActivityPyatBinding
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

@Suppress("DEPRECATION", "NestedBlockDepth", "UnnecessaryParentheses", "MagicNumber")
class PyatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPyatBinding
    private var matrixSize = 4
    private var buttonSize = 300
    private var tableOffsetX = 125
    private var tableOffsetY = 400

    private var buttons = mutableListOf<Button>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityPyatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initGame()
        val bt2 = findViewById<ImageView>(R.id.button2)

        bt2.setOnClickListener {
            val intent = Intent(this, PyatActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.two_intent, R.anim.one_intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initGame() {
        val l1 = IntStream.range(0, matrixSize).toList().toMutableList()
        val l2 = IntStream.range(0, matrixSize).toList().toMutableList()
        l1.shuffle()
        l2.shuffle()
        l1.forEach { x ->
            l2.forEach { y ->
                val button = createButton(y.toFloat(), x.toFloat())
                buttons.add(button)
                binding.container.addView(button)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun moveButton(currentButton: Button) {
        Log.i("Current Pos", "x: ${currentButton.x}  y: ${currentButton.y}")
        val neighbours =
            Position(
                (currentButton.x - tableOffsetX) / buttonSize,
                (currentButton.y - tableOffsetY) / buttonSize
            ).neighbours(
                matrixSize
            )

        val button = binding.container.findViewWithTag<Button>("target")
        val x = (button.x - tableOffsetX) / buttonSize
        val y = (button.y - tableOffsetY) / buttonSize
        val txt = findViewById<TextView>(R.id.winner1)
        val bt2 = findViewById<ImageView>(R.id.button2)
        if (neighbours.contains(Position(x, y))) {
            Log.i("Move", "valid")
            Log.i("Next Pos", "x: ${button.x}  y: ${button.y}")

            val nextPC = Position(currentButton.x, currentButton.y) - Position(button.x, button.y)
            val nextPN = Position(button.x, button.y) - Position(currentButton.x, currentButton.y)
            currentButton.translationX = currentButton.x + nextPC.x
            currentButton.translationY = currentButton.y + nextPC.y
            button.translationX = button.x + nextPN.x
            button.translationY = button.y + nextPN.y

        } else {
            Log.i("Move", "invalid")
        }
        if (check()) {
            txt.text = "Вы победили!"
            txt.setTextColor(Color.GREEN)
            bt2.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun check(): Boolean {
        var b = true
        val bttns = mutableListOf<Button>()
        IntStream.range(1, matrixSize * matrixSize + 1).forEach {
            val button = binding.container.findViewById<Button>(it)
            bttns += button
        }
        var index = 0
        IntStream.range(0, matrixSize).forEach { i ->
            IntStream.range(0, matrixSize).forEach { j ->
                if (bttns[index].x != j * buttonSize.toFloat() || bttns[index].y != i * buttonSize.toFloat()) {
                    b = false
                }
                index++
            }
        }
        return b
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createButton(x: Float, y: Float): Button {
        val button = Button(this)
        button.x = x * buttonSize + tableOffsetX
        button.y = y * buttonSize + tableOffsetY
        button.width = buttonSize
        button.height = buttonSize
        button.id = View.generateViewId()
        button.setOnClickListener {
            moveButton(button)
        }
        button.text = "${button.id}"
        button.background.setTint(Color.DKGRAY)
        if (button.id == matrixSize * matrixSize) {
            button.text = " "
            button.tag = "target"
            button.background.setTint(Color.parseColor("#159DC3"))
        }
        return button
    }
}