package ru.dekabrsky.italks.navigation

import ru.dekabrsky.callersbase.presentation.view.CallersBaseFlowFragment
import ru.dekabrsky.callersbase_common.presentation.model.CallersBasesFlowScreenArgs
import ru.dekabrsky.dialings.presentation.view.DialingsFlowFragment
import ru.dekabrsky.dialings_common.presentation.model.DialingsFlowScreenArgs
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import ru.dekabrsky.italks.testerSettings.presentation.view.TesterSettingsFlowFragment
import ru.dekabrsky.scenarios.presentation.view.ScenariosFlowFragment
import ru.dekabrsky.stats.presentation.view.StatsFlowFragment
import javax.inject.Inject

class AppFlowFragmentProvider @Inject constructor(): FlowFragmentProvider {

    override fun provideFlowFragment(screenName: String?, data: Any?): BasicFlowFragment? {
        return when (screenName) {
            Flows.Main.name -> TabsFlowFragment.newInstance()
            Flows.CallersBase.name ->
                CallersBaseFlowFragment.newInstance(data as CallersBasesFlowScreenArgs)
            Flows.Scenarios.name -> ScenariosFlowFragment.newInstance()
            Flows.Dialing.name ->
                DialingsFlowFragment.newInstance(data as DialingsFlowScreenArgs)
            Flows.Stats.name ->
                StatsFlowFragment.newInstance()
            Flows.TesterSettings.name ->
                TesterSettingsFlowFragment.newInstance()
            else -> null
        }
    }
}