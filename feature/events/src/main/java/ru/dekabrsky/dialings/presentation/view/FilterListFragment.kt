package ru.dekabrsky.dialings.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.FiltersFragmentBinding
import ru.dekabrsky.dialings.presentation.adapter.FiltersAdapter
import ru.dekabrsky.dialings.presentation.model.FilterScreenArgs
import ru.dekabrsky.dialings.presentation.presenter.DialingsListPresenter
import ru.dekabrsky.dialings.presentation.presenter.FiltersListPresenter
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class FilterListFragment: BasicFragment(), FilterListView {
    override val layoutRes = R.layout.filters_fragment

    private val binding by viewBinding(FiltersFragmentBinding::bind)

    private val adapter by lazy {
        FiltersAdapter(
            presenter::onItemCheckedChanged,
            presenter::isChecked
        )
    }

    private lateinit var args: FilterScreenArgs

    @InjectPresenter
    lateinit var presenter: FiltersListPresenter

    @ProvidePresenter
    fun providePresenter(): FiltersListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_DIALINGS, scopeName)
            .module { bind(FilterScreenArgs::class.java).toInstance(args) }
            .getInstance(FiltersListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filtersRecycler.adapter = adapter
        binding.saveBtn.setOnClickListener { presenter.onSaveClick() }
        binding.toolbar.title = args.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        (parentFragment as DialingsFlowFragment).setNavBarVisibility(false)
    }

    override fun setList(list: List<String>) {
        adapter.updateItems(list)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance(args: FilterScreenArgs) = FilterListFragment().apply { this.args = args }
    }
}