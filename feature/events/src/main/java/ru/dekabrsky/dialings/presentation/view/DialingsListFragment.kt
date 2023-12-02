package ru.dekabrsky.dialings.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import main.utils.gone
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.FragmentDialingListBinding
import ru.dekabrsky.dialings.presentation.adapter.DialingListAdapter
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.presentation.presenter.DialingsListPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class DialingsListFragment : BasicFragment(), DialingsListView {

    override val layoutRes: Int = R.layout.fragment_dialing_list

    private val adapter by lazy {
        DialingListAdapter(
            onItemClick = presenter::onToDetailsClick,
            onRunClick = presenter::onRunClick
        )
    }

    private val binding by viewBinding(FragmentDialingListBinding::bind)

    private var sortByNameItem: MenuItem? = null
    private var sortByDateAscItem: MenuItem? = null
    private var sortByDateDescItem: MenuItem? = null

    @InjectPresenter
    lateinit var presenter: DialingsListPresenter

    @ProvidePresenter
    fun providePresenter(): DialingsListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_DIALINGS, scopeName)
            .getInstance(DialingsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as DialingsFlowFragment).setNavBarVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = adapter
        binding.toolbar.setTitle(R.string.dialings_title)

        binding.filters.setOnCheckedChangeListener { _, checkedId ->
            presenter.onFilterChanged(checkedId)
        }

        binding.allChip.isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dialings_menu, menu)

        sortByNameItem = menu.findItem(R.id.sort_name)
        sortByDateAscItem = menu.findItem(R.id.sort_date_asc)
        sortByDateDescItem = menu.findItem(R.id.sort_date_desc)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorting -> {
            }
            R.id.search -> {
            }
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

    override fun setItems(items: List<DialingUiModel>) {
        adapter.updateItems(items)
    }

    override fun showEmptyLayout() {
        binding.emptyLayout.visible()
        binding.basesCardsList.gone()
    }

    override fun hideEmptyLayout() {
        binding.emptyLayout.gone()
        binding.basesCardsList.visible()
    }

    override fun showNoConnectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Нет подключения")
        builder.setMessage(
            "Приложение не может подключиться к серверу. Убедитесь, что он доступен из вашей сети"
        )
        builder.show()
    }

    override fun setFilter(chipId: Int) {
        binding.filters.check(chipId)
    }

    override fun showRunNowDialog(id: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Запустить сейчас?")
        builder.setPositiveButton("Да") { _, _ -> presenter.runNow(id) }
        builder.setNegativeButton("Нет") { _, _ -> }
        builder.show()
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = DialingsListFragment()
    }
}