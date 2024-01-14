package ru.dekabrsky.italks.game.view.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.databinding.FragmentFifteenBinding
import ru.dekabrsky.italks.game.view.FifteenView
import ru.dekabrsky.italks.game.view.model.GridAdapter
import ru.dekabrsky.italks.game.view.presenter.FifteenPresenter
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.*

@Suppress("NestedBlockDepth", "UnnecessaryParentheses", "MagicNumber")
class FifteenFragment : BasicFragment(), FifteenView {
    private val binding by viewBinding(FragmentFifteenBinding::bind)

    private lateinit var mGrid: GridView
    private lateinit var mAdapter: GridAdapter
    private lateinit var mStepScreen: TextView

    private var stepCount: Int = 0
    private var progress: Int = 0
    private val sizeGrid: Int = 4

    override val layoutRes = R.layout.fragment_fifteen

    @InjectPresenter
    lateinit var presenter: FifteenPresenter

    @ProvidePresenter
    fun providePresenter(): FifteenPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(FifteenPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRestart.setOnClickListener { presenter.restart() }
        binding.iconClose.setOnClickListener { presenter.exitGame() }

        mStepScreen = binding.stepview

        stepCount = 0
        mStepScreen.text = "Попыток: $stepCount"

        mGrid = binding.field
        mGrid.isEnabled = true

        mAdapter = GridAdapter(requireActivity(), sizeGrid, sizeGrid)
        mGrid.adapter = mAdapter

        mGrid.setOnItemClickListener { _, _, position, _ ->
            mAdapter.checkOpenCells()
            if (mAdapter.openCell(position)) {
                stepCount++
                mStepScreen.text = "Попыток: $stepCount"
            }

            if (mAdapter.checkGameOver()) {
                progress = 130 - stepCount
                presenter.saveProgress(score = progress, usePillMultiplier = true)
                snackBarView(requireActivity().findViewById(R.id.container))
                binding.buttonRestart.visibility = View.VISIBLE
                binding.hedgehog.visibility = View.INVISIBLE
            }
        }
    }

    private fun snackBarView(view: View){
        val snackBar = Snackbar.make(view, "  Ты заработал $progress коинов", Snackbar.LENGTH_SHORT)
        val snackBarLayout: View = snackBar.view
        val textView: TextView =
            snackBarLayout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin_mini, 0, 0, 0)
        snackBar.show()
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = FifteenFragment()
    }
}