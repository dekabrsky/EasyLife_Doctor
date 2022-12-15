package ru.dekabrsky.materials.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes.SCOPE_APP
import ru.dekabrsky.italks.scopes.Scopes.SCOPE_FLOW_MATERIALS
import ru.dekabrsky.materials.R
import ru.dekabrsky.materials.presentation.presenter.MaterialsFlowPresenter
import toothpick.Toothpick
import javax.inject.Inject

class MaterialsFlowFragment : BasicFlowFragment(), MaterialsFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = SCOPE_FLOW_MATERIALS

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Materials.SCREEN_MATERIALS_LIST -> MaterialsListFragment.newInstance()
//                    Flows.Materials.SCREEN_MATERIAL_DETAILS ->
//                        MaterialDetailsFragment.newInstance(data as MaterialDetailsUiModel)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: MaterialsFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(SCOPE_APP, scopeName)
            .installNavigation()
            .inject(this)
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        Toothpick.closeScope(scopeName)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = MaterialsFlowFragment()
    }
}