package ru.dekabrsky.scenarios.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.easylife.basic.di.inject
import ru.dekabrsky.easylife.basic.fragments.BasicFlowFragment
import ru.dekabrsky.easylife.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.easylife.basic.navigation.di.moduleFlow
import ru.dekabrsky.easylife.basic.navigation.router.AppRouter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.flows.Flows.Common.SCREEN_BOTTOM_INFO
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.easylife.tabs.presentation.fragment.TabsFlowFragment
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.presentation.model.PatientsCodesScreenArgs
import ru.dekabrsky.scenarios.presentation.model.SelectParentArgs
import ru.dekabrsky.scenarios.presentation.presenter.ScenariosFlowPresenter
import ru.dekabrsky.simpleBottomsheet.view.fragment.SimpleInfoBottomSheet
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import toothpick.Toothpick
import javax.inject.Inject

class PatientsFlowFragment : BasicFlowFragment(), PatientsFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_PATIENTS

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Patients.SCREEN_PATIENTS_LIST -> PatientsListFragment.newInstance()
                    Flows.Patients.SCREEN_PATIENT_DETAILS ->
                        PatientDetailsFragment.newInstance(data as ContactEntity)
                    Flows.Patients.SCREEN_PATIENTS_CODES ->
                        PatientsCodesFragment.newInstance(data as PatientsCodesScreenArgs)
                    Flows.Patients.SCREEN_INVITE_PATIENT ->
                        InvitePatientFragment.newInstance()
                    Flows.Patients.SCREEN_SELECT_PARENT ->
                        SelectParentFragment.newInstance(data as SelectParentArgs)
                    SCREEN_BOTTOM_INFO -> SimpleInfoBottomSheet.newInstance(data as BottomSheetScreenArgs)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: ScenariosFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP, scopeName)
            .moduleFlow()
            .inject(this)
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        Toothpick.closeScope(scopeName)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    fun setNavBarVisibility(isVisible: Boolean) {
        (parentFragment as TabsFlowFragment).setNavBarVisibility(isVisible)
    }

    companion object {
        fun newInstance() = PatientsFlowFragment()
    }
}