package ru.dekabrsky.materials.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.materials.R
import ru.dekabrsky.materials.databinding.FragmentMaterialsListBinding
import ru.dekabrsky.materials.presentation.adapter.MaterialsListAdapter
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel
import ru.dekabrsky.materials.presentation.presenter.MaterialsListPresenter
import toothpick.Toothpick

class MaterialsListFragment: BasicFragment(), MaterialsListView {

    override val layoutRes: Int = R.layout.fragment_materials_list

    private val adapter by lazy { MaterialsListAdapter(presenter::onMaterialClick) }

    private val binding by viewBinding(FragmentMaterialsListBinding::bind)

    @InjectPresenter
    lateinit var presenter: MaterialsListPresenter

    @ProvidePresenter
    fun providePresenter(): MaterialsListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_MATERIALS, scopeName)
            .getInstance(MaterialsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as MaterialsFlowFragment).setNavBarVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.list.adapter = adapter
        binding.toolbar.title = "Материалы"
    }


    override fun setMaterialsList(items: List<MaterialDetailsUiModel>) {
        adapter.updateItems(items)
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = MaterialsListFragment()
    }
}