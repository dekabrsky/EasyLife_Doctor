package ru.dekabrsky.scenarios.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentScenariosBinding
import ru.dekabrsky.scenarios.presentation.adapter.ScenariosAdapter
import ru.dekabrsky.scenarios.presentation.presenter.ScenariosListPresenter
import toothpick.Toothpick


class PatientsListFragment: BasicFragment(), PatientsListView {

    override val layoutRes: Int = R.layout.fragment_scenarios

    private val adapter by lazy { ScenariosAdapter(presenter::onItemClick) }

    private val binding by viewBinding(FragmentScenariosBinding::bind)

    private var sortByNameItem: MenuItem? = null
    private var sortByDateAscItem: MenuItem? = null
    private var sortByDateDescItem: MenuItem? = null

    @InjectPresenter
    lateinit var presenter: ScenariosListPresenter

    @ProvidePresenter
    fun providePresenter(): ScenariosListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_SCENARIOS, scopeName)
            .getInstance(ScenariosListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = adapter
        binding.toolbar.setTitle(R.string.scenarios_title)
        binding.invitePatient.setOnClickListener {
            showInviteCodeConfirmation()
        }
    }

    private fun showInviteCodeConfirmation() {
        AlertDialog.Builder(context)
            .setTitle("Приглашение нового пациента")
            .setMessage("Пациенту исполнилось 15 лет?")
            .setPositiveButton("Да") { _, _ -> presenter.generatePatients(true) }
            .setNegativeButton("Нет")  { _, _ -> presenter.generatePatients(false) }
            .setNeutralButton("Отмена", null)
            .setIcon(R.drawable.ic_round_group_24)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.scenarios_menu, menu)

        sortByNameItem = menu.findItem(R.id.sort_name)
        sortByDateAscItem = menu.findItem(R.id.sort_date_asc)
        sortByDateDescItem = menu.findItem(R.id.sort_date_desc)
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as ScenariosFlowFragment).setNavBarVisibility(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorting -> {}
            R.id.search -> {}
            R.id.sort_date_asc -> {
                presenter.loadSortByDateAsc()
                sortByDateAscItem?.isChecked = true
                sortByDateDescItem?.isChecked = false
                sortByNameItem?.isChecked = false
            }
            R.id.sort_date_desc -> {
                presenter.loadSortByDateDesc()
                sortByDateAscItem?.isChecked = false
                sortByDateDescItem?.isChecked = true
                sortByNameItem?.isChecked = false
            }
            R.id.sort_name -> {
                presenter.loadSortByName()
                sortByDateAscItem?.isChecked = false
                sortByDateDescItem?.isChecked = false
                sortByNameItem?.isChecked = true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setItems(items: List<ScenarioItemUiModel>) {
        adapter.updateItems(items)
    }

    override fun showEmptyLayout() {
        binding.emptyLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = PatientsListFragment()
    }
}