package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentPatientsBinding
import ru.dekabrsky.scenarios.presentation.adapter.PatientsAdapter
import ru.dekabrsky.scenarios.presentation.presenter.PatientsListPresenter
import toothpick.Toothpick


class PatientsListFragment: BasicFragment(), PatientsListView {

    override val layoutRes: Int = R.layout.fragment_patients

    private val adapter by lazy { PatientsAdapter(presenter::onItemClick) }

    private val binding by viewBinding(FragmentPatientsBinding::bind)

    @InjectPresenter
    lateinit var presenter: PatientsListPresenter

    @ProvidePresenter
    fun providePresenter(): PatientsListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_PATIENTS, scopeName)
            .getInstance(PatientsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = adapter
        binding.basesCardsList.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        binding.toolbar.setTitle(R.string.scenarios_title)
        binding.invitePatient.setOnClickListener { presenter.onInviteClick() }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as PatientsFlowFragment).setNavBarVisibility(true)
    }

    override fun setItems(items: List<ContactEntity>) {
        adapter.updateItems(items)
    }

    override fun showEmptyLayout() {
        binding.emptyLayout.visible()
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = PatientsListFragment()
    }
}