package ru.dekabrsky.stats.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
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
        binding.logoutBtn.setOnClickListener { presenter.onLogoutClick() }
    }

    override fun showMyInfo(infoEntity: UserInfoEntity?) {
         infoEntity?.let {
             binding.myProfile.title.setText(R.string.myAccount)
             binding.myProfile.value.text = it.name
             binding.myProfile.speciality.text = it.role.desc
         }
    }

    override fun showChildInfo(children: List<ContactEntity>) {
        adapter.updateItems(children)
    }

    companion object{
        fun newInstance() = AdultProfileFragment()
    }
}