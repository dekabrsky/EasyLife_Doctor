package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentSelectParentBinding
import ru.dekabrsky.scenarios.presentation.adapter.SelectParentAdapter
import ru.dekabrsky.scenarios.presentation.model.SelectParentArgs
import ru.dekabrsky.scenarios.presentation.presenter.SelectParentPresenter
import toothpick.Toothpick

class SelectParentFragment : BasicFragment(), SelectParentView {
    override val layoutRes: Int = R.layout.fragment_select_parent

    @InjectPresenter
    lateinit var presenter: SelectParentPresenter

    private var args: SelectParentArgs? = null

    private val adapter by lazy {
        SelectParentAdapter(
            onItemClick = { item -> presenter.onItemClick(item) },
            isChecked = { id -> presenter.isItemChecked(id) }
        )
    }

    private val binding by viewBinding(FragmentSelectParentBinding::bind)

    @ProvidePresenter
    fun providePresenter(): SelectParentPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_PATIENTS, scopeName)
            .module { bind(SelectParentArgs::class.java).toInstance(args) }
            .getInstance(SelectParentPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.existingParentsList.adapter = adapter
    }

    override fun setList(variants: List<ContactEntity>) {
        adapter.updateItems(variants)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance(args: SelectParentArgs) = SelectParentFragment().apply { this.args = args }
    }
}