package ru.dekabrsky.dialings.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import main.utils.gone
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.FragmentDialingListBinding
import ru.dekabrsky.dialings.presentation.adapter.DialingListAdapter
import ru.dekabrsky.dialings.presentation.presenter.DialingsListPresenter
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
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
        binding.basesCardsList.adapter = adapter
        binding.toolbar.setTitle(R.string.dialings_title)

        binding.filters.setOnCheckedChangeListener { _, checkedId ->
            presenter.onFilterChanged(checkedId)
        }

        binding.allChip.isChecked = true
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