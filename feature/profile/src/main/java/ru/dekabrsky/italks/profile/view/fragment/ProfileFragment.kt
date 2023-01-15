package ru.dekabrsky.italks.profile.view.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.profile.R
import ru.dekabrsky.italks.profile.databinding.FragmentProfileBinding
import ru.dekabrsky.italks.profile.view.DailyActivity
import ru.dekabrsky.italks.profile.view.ProfileView
import ru.dekabrsky.italks.profile.view.presenter.ProfilePresenter
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.*

class ProfileFragment : BasicFragment(), ProfileView {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private var question = 0
    private var answer = ""

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_GAME, scopeName)
            .getInstance(ProfilePresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_profile


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.textView9.setText(answer)

        val questions = resources.getStringArray(R.array.Questions)
        val spinner = binding.spinner
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item, questions
        )
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                Log.d("No", "nothing")
                val pref: SharedPreferences =
                    requireActivity().getSharedPreferences(
                        "Control",
                        AppCompatActivity.MODE_PRIVATE
                    )
                val editor = pref.edit()
                editor.putInt("2", question).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("No", "nothing")
            }
        }
        binding.textView16.setOnClickListener {
            answer = binding.textView9.text.toString()
            val pref: SharedPreferences =
                requireActivity().getSharedPreferences(
                    "Control",
                    AppCompatActivity.MODE_PRIVATE
                )
            val editor = pref.edit()
            editor.putString("1", answer).apply()
            Toast.makeText(this@ProfileFragment.requireContext(), "Вопрос успешно сохранен", Toast.LENGTH_SHORT).show()
        }
        binding.textView19.setOnClickListener {
            presenter.generateParent()
        }
        binding.iV15.setOnClickListener {
            val intent = Intent(this@ProfileFragment.requireContext(), DailyActivity::class.java)
            startActivity(intent)
        }
        tapCat()
    }

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }

    private fun init() {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("Control", AppCompatActivity.MODE_PRIVATE)
        answer = pref.getString("1", answer)!!
        question = pref.getInt("2", question)
    }

    private fun tapCat() {
        val animationBounce = AnimationUtils.loadAnimation(this.requireActivity(), R.anim.bounce)
        binding.cardView.setOnClickListener {
            binding.catview.startAnimation(animationBounce)
        }
        binding.cardView1.setOnClickListener {
            binding.cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonepink)
            binding.cardView1.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonepink)
            binding.cardView2.foreground = ContextCompat.getDrawable(requireContext(), R.color.brown)
            binding.cardView3.foreground = ContextCompat.getDrawable(requireContext(), R.color.orange)
            binding.cardView4.foreground = ContextCompat.getDrawable(requireContext(), R.color.grey1)
        }
        binding.cardView2.setOnClickListener {
            binding.cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonebrown)
            binding.cardView1.foreground = ContextCompat.getDrawable(requireContext(), R.color.pink)
            binding.cardView2.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonebrown)
            binding.cardView3.foreground = ContextCompat.getDrawable(requireContext(), R.color.orange)
            binding.cardView4.foreground = ContextCompat.getDrawable(requireContext(), R.color.grey1)
        }
        binding.cardView3.setOnClickListener {
            binding.cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.cardfoneorange)
            binding.cardView1.foreground = ContextCompat.getDrawable(requireContext(), R.color.pink)
            binding.cardView2.foreground = ContextCompat.getDrawable(requireContext(), R.color.brown)
            binding.cardView3.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.cardfoneorange)
            binding.cardView4.foreground = ContextCompat.getDrawable(requireContext(), R.color.grey1)
        }
        binding.cardView4.setOnClickListener {
            binding.cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonegrey)
            binding.cardView1.foreground = ContextCompat.getDrawable(requireContext(), R.color.pink)
            binding.cardView2.foreground = ContextCompat.getDrawable(requireContext(), R.color.brown)
            binding.cardView3.foreground = ContextCompat.getDrawable(requireContext(), R.color.orange)
            binding.cardView4.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.cardfonegrey)
        }
    }

    override fun setParentCode(code: Int) {
        binding.textView20.text = code.toString()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}