package ru.dekabrsky.italks.game.view.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.Progress
import ru.dekabrsky.italks.game.data.ProgressDb
import ru.dekabrsky.italks.game.databinding.FragmentFifteenBinding
import ru.dekabrsky.italks.game.view.FifteenView
import ru.dekabrsky.italks.game.view.model.Position
import ru.dekabrsky.italks.game.view.presenter.FifteenPresenter
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

@Suppress("NestedBlockDepth", "UnnecessaryParentheses", "MagicNumber")
class FifteenFragment : BasicFragment(), FifteenView {
    private val binding by viewBinding(FragmentFifteenBinding::bind)

    private var matrixSize = 4
    private var buttonSize = 300
    private var tableOffsetX = 125
    private var tableOffsetY = 400

    private var buttons = mutableListOf<Button>()

    override val layoutRes = R.layout.fragment_fifteen

    @InjectPresenter
    lateinit var presenter: FifteenPresenter

    @ProvidePresenter
    fun providePresenter(): FifteenPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(FifteenPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRestart.setOnClickListener { initGame() }
        binding.iconClose.setOnClickListener { presenter.exitGame() }
        binding.iconHome.setOnClickListener { presenter.goToHome() }
        binding.iconGamePad.setOnClickListener { presenter.goToGarden() }

        initGame()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initGame() {
        val l1 = IntStream.range(0, matrixSize).toList().toMutableList()
        val l2 = IntStream.range(0, matrixSize).toList().toMutableList()
        l1.shuffle()
        l2.shuffle()
        l1.forEach { x ->
            l2.forEach { y ->
                val button = createButton(y, x)
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
            val db = ProgressDb.getDb(requireContext())
            binding.winner1.text = "Вы победили!"
            binding.winner1.setTextColor(Color.GREEN)
            binding.buttonRestart.visibility = View.VISIBLE
            val item = Progress(null,
                "Fifteen",
                200
            )
            Thread{
                db.getDao().insertProgress(item)
            }.start()
            snackBarView(requireActivity().findViewById(R.id.container))
        }
    }

    private fun snackBarView(view: View){
        val snackBar = Snackbar.make(view, "  Ты заработал 200 коинов", Snackbar.LENGTH_SHORT)
        val snackBarLayout: View = snackBar.view
        val textView: TextView =
            snackBarLayout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin_mini, 0, 0, 0)
        snackBar.show()
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
    private fun createButton(x: Int, y: Int): Button {
        val button = Button(requireContext())
        button.x = x.toFloat() * buttonSize + tableOffsetX
        button.y = y.toFloat() * buttonSize + tableOffsetY
        button.width = buttonSize
        button.height = buttonSize
        button.id = matrixSize * x + y + 1
        button.setOnClickListener {
            moveButton(button)
        }
        button.text = "${button.id}"
        button.background = ContextCompat.getDrawable(requireContext(), R.drawable.steak)
        button.setTextAppearance(R.style.FifteenCardText)
        if (button.id == matrixSize * matrixSize) {
            button.text = " "
            button.tag = "target"
            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.chicken)
        }
        return button
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = FifteenFragment()
    }
}