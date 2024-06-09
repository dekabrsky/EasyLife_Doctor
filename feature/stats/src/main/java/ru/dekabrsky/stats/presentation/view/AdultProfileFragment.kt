package ru.dekabrsky.stats.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.stats.R
import ru.dekabrsky.stats.databinding.FragmentAdultProfileBinding
import ru.dekabrsky.stats.presentation.adapter.ChildrenAdapter
import ru.dekabrsky.stats.presentation.presenter.AdultProfilePresenter
import toothpick.Toothpick

class AdultProfileFragment: BasicFragment(), AdultProfileView {

    override val layoutRes: Int
        get() = R.layout.fragment_adult_profile

    @InjectPresenter
    lateinit var presenter: AdultProfilePresenter

    private val binding by viewBinding(FragmentAdultProfileBinding::bind)

    private val adapter by lazy { ChildrenAdapter() }

    @ProvidePresenter
    fun providePresenter(): AdultProfilePresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_ADULT_PROFILE, scopeName)
            .getInstance(AdultProfilePresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.stats_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.toolbar.setTitle(R.string.general_stats)
        binding.childrenRecycler.adapter = adapter
        binding.logoutBtn.setOnClickListener { presenter.onLogoutClicked() }
        binding.terms.setOnClickListener { presenter.onTermsTextClick() }
        binding.policy.setOnClickListener { presenter.onPolicyTextClick() }
    }

    override fun showMyInfo(infoEntity: UserInfoEntity?) {
         infoEntity?.let {
             binding.myProfile.title.setText(R.string.myAccount)
             binding.myProfile.value.text = it.name
             binding.myProfile.speciality.text =
                 context?.getString(R.string.nickname_role_pattern, it.nickname, it.role.desc)
         }
    }

    override fun showChildInfo(children: List<ContactEntity>) {
        adapter.updateItems(children)
    }

    override fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.confirm_logout_msg))
        builder.setTitle(getString(R.string.confirm_logout_title))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ -> presenter.onLogoutConfirmed() }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.show()
    }

    companion object{
        fun newInstance() = AdultProfileFragment()
    }
}