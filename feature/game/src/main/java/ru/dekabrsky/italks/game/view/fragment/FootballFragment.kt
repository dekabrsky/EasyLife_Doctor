package ru.dekabrsky.italks.game.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.data.model.Progress
import ru.dekabrsky.italks.game.data.model.ProgressDb
import ru.dekabrsky.italks.game.databinding.FragmentFootballBinding
import ru.dekabrsky.italks.game.view.FootballView
import ru.dekabrsky.italks.game.view.presenter.FootballPresenter
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.Random

@Suppress("LongMethod", "CyclomaticComplexMethod", "ComplexCondition", "MaxLineLength", "MagicNumber")
class FootballFragment : BasicFragment(), FootballView {
    private var pointsOfHuman = 0
    private var pointsOfAI = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override val layoutRes = R.layout.fragment_football

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    private val viewBinding by viewBinding(FragmentFootballBinding::bind)

    @InjectPresenter
    lateinit var presenter: FootballPresenter

    @ProvidePresenter
    fun providePresenter(): FootballPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(FootballPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_football, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("krestikNolik", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        pointsOfHuman = sharedPreferences.getInt("pointsOfHuman", 0)
        viewBinding.humanPoints.text = "" + pointsOfHuman
        pointsOfAI = sharedPreferences.getInt("pointsOfAI", 0)
        viewBinding.pcPoints.text = "" + pointsOfAI
        viewBinding.textView.text = ""
        @SuppressLint("DiscouragedApi") val idView1 = resources.getIdentifier("button1", "id", context?.packageName)
        val eventView1 = view.findViewById<View>(idView1)
        eventView1.setOnClickListener {
            if (viewBinding.button1.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button1.setImageResource(R.drawable.basket)
                viewBinding.button1.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView2 = resources.getIdentifier("button2", "id", context?.packageName)
        val eventView2 = view.findViewById<View>(idView2)
        eventView2.setOnClickListener {
            if (viewBinding.button2.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button2.setImageResource(R.drawable.basket)
                viewBinding.button2.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView3 = resources.getIdentifier("button3", "id", context?.packageName)
        val eventView3 = view.findViewById<View>(idView3)
        eventView3.setOnClickListener {
            if (viewBinding.button3.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button3.setImageResource(R.drawable.basket)
                viewBinding.button3.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView4 = resources.getIdentifier("button4", "id", context?.packageName)
        val eventView4 = view.findViewById<View>(idView4)
        eventView4.setOnClickListener {
            if (viewBinding.button4.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button4.setImageResource(R.drawable.basket)
                viewBinding.button4.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView5 = resources.getIdentifier("button5", "id", context?.packageName)
        val eventView5 = view.findViewById<View>(idView5)
        eventView5.setOnClickListener {
            if (viewBinding.button5.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button5.setImageResource(R.drawable.basket)
                viewBinding.button5.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView6 = resources.getIdentifier("button6", "id", context?.packageName)
        val eventView6 = view.findViewById<View>(idView6)
        eventView6.setOnClickListener {
            if (viewBinding.button6.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button6.setImageResource(R.drawable.basket)
                viewBinding.button6.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView7 = resources.getIdentifier("button7", "id", context?.packageName)
        val eventView7 = view.findViewById<View>(idView7)
        eventView7.setOnClickListener {
            if (viewBinding.button7.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button7.setImageResource(R.drawable.basket)
                viewBinding.button7.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView8 = resources.getIdentifier("button8", "id", context?.packageName)
        val eventView8 = view.findViewById<View>(idView8)
        eventView8.setOnClickListener {
            if (viewBinding.button8.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button8.setImageResource(R.drawable.basket)
                viewBinding.button8.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }
        @SuppressLint("DiscouragedApi") val idView9 = resources.getIdentifier("button9", "id", context?.packageName)
        val eventView9 = view.findViewById<View>(idView9)
        eventView9.setOnClickListener {
            if (viewBinding.button9.drawable == null && viewBinding.textView.text === "") {
                viewBinding.button9.setImageResource(R.drawable.basket)
                viewBinding.button9.tag = R.drawable.basket
                isPlayerWinner
                if (viewBinding.textView.text === "") {
                    hodAI()
                }
            }
        }

        viewBinding.restart.setOnClickListener { presenter.restart() }
        viewBinding.exitGame.setOnClickListener { presenter.exitGame() }
        viewBinding.goToHome.setOnClickListener { presenter.goToHome() }
        viewBinding.goToGarden.setOnClickListener { presenter.goToGarden() }
    }

    @get:SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    val isPlayerWinner: Unit
        get() {
            if (viewBinding.button1.tag.toString().toInt() == R.drawable.basket && viewBinding.button2.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button4.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button6.tag.toString().toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button7.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button8.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button9.tag.toString()
                    .toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button1.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button4.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button7.tag.toString()
                    .toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button2.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button8.tag.toString().toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button6.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button9.tag.toString().toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button1.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button9.tag.toString().toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.basket && viewBinding.button7.tag.toString().toInt() == R.drawable.basket
            ) {
                viewBinding.textView.setText(R.string.winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                viewBinding.anim.playAnimation()
                viewBinding.anim.repeatCount = LottieDrawable.REVERSE
                viewBinding.anim.visibility = View.VISIBLE
                pointsOfHuman++
                saveProgress()
                viewBinding.humanPoints.text = "" + pointsOfHuman
                editor.putInt("pointsOfHuman", pointsOfHuman)
                editor.apply()
            } else if (viewBinding.button1.drawable != null && viewBinding.button2.drawable != null && viewBinding.button3.drawable != null && viewBinding.button4.drawable != null && viewBinding.button5.drawable != null && viewBinding.button6.drawable != null && viewBinding.button7.drawable != null && viewBinding.button8.drawable != null && viewBinding.button9.drawable != null) {
                viewBinding.textView.setText(R.string.draw_message)
                viewBinding.restart.visibility = View.VISIBLE
            }
        }

    @get:SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    val isPCWinner: Unit
        get() {
            Log.i("hodAI", "pointsOfAI - $pointsOfAI")
            if (viewBinding.button1.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button2.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button4.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button6.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button7.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button8.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button9.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button1.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button4.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button7.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button2.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button8.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button6.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button9.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button1.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button9.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            } else if (viewBinding.button3.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button5.tag.toString()
                    .toInt() == R.drawable.football_ball && viewBinding.button7.tag.toString()
                    .toInt() == R.drawable.football_ball
            ) {
                viewBinding.textView.setText(R.string.pc_winner_message)
                viewBinding.restart.visibility = View.VISIBLE
                pointsOfAI++
                viewBinding.pcPoints.text = "" + pointsOfAI
                editor.putInt("pointsOfAI", pointsOfAI)
                editor.apply()
            }
        }

    private fun saveProgress(){
        val db = ProgressDb.getDb(requireContext())
        val item = Progress(null,
            "Football",
            40
        )
        Thread{
            db.getDao().insertProgress(item)
        }.start()
        snackBarView(requireActivity().findViewById(R.id.football_layout))
    }

    private fun snackBarView(view: View){
        val snackBar = Snackbar.make(view, "  Ты заработал 40 коинов", Snackbar.LENGTH_SHORT)
        val snackBarLayout: View = snackBar.view
        val textView: TextView =
            snackBarLayout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin_mini, 0, 0, 0)
        snackBar.show()
    }

    private fun hodAI() {
        val random = Random()
        val buttonPcClick = 1 + random.nextInt(9)
        Log.i("hodAI", "buttonPcClick - $buttonPcClick")
        when (buttonPcClick) {
            1 -> if (viewBinding.button1.drawable == null) {
                viewBinding.button1.setImageResource(R.drawable.football_ball)
                viewBinding.button1.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            2 -> if (viewBinding.button2.drawable == null) {
                viewBinding.button2.setImageResource(R.drawable.football_ball)
                viewBinding.button2.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            3 -> if (viewBinding.button3.drawable == null) {
                viewBinding.button3.setImageResource(R.drawable.football_ball)
                viewBinding.button3.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            4 -> if (viewBinding.button4.drawable == null) {
                viewBinding.button4.setImageResource(R.drawable.football_ball)
                viewBinding.button4.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            5 -> if (viewBinding.button5.drawable == null) {
                viewBinding.button5.setImageResource(R.drawable.football_ball)
                viewBinding.button5.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            6 -> if (viewBinding.button6.drawable == null) {
                viewBinding.button6.setImageResource(R.drawable.football_ball)
                viewBinding.button6.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            7 -> if (viewBinding.button7.drawable == null) {
                viewBinding.button7.setImageResource(R.drawable.football_ball)
                viewBinding.button7.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            8 -> if (viewBinding.button8.drawable == null) {
                viewBinding.button8.setImageResource(R.drawable.football_ball)
                viewBinding.button8.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }

            9 -> if (viewBinding.button9.drawable == null) {
                viewBinding.button9.setImageResource(R.drawable.football_ball)
                viewBinding.button9.tag = R.drawable.football_ball
                isPCWinner
            } else {
                hodAI()
            }
        }
    }

    companion object {
        fun newInstance() = FootballFragment()
    }
}