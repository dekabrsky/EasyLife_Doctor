package ru.dekabrsky.italks.navigation

import ru.dekabrsky.callersbase.presentation.view.ChatFlowFragment
import ru.dekabrsky.callersbase_common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.dialings.presentation.view.DialingsFlowFragment
import ru.dekabrsky.dialings_common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.italks.flows.Flows
import main.java.ru.dekabrsky.italks.game.GameFlowFragment
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.italks.testerSettings.presentation.view.TesterSettingsFlowFragment
import ru.dekabrsky.login.presentation.view.LoginFlowFragment
import ru.dekabrsky.scenarios.presentation.view.ScenariosFlowFragment
import ru.dekabrsky.stats.presentation.view.StatsFlowFragment
import javax.inject.Inject

class AppFlowFragmentProvider @Inject constructor(): FlowFragmentProvider {

    override fun provideFlowFragment(screenName: String?, data: Any?): BasicFlowFragment? {
        return when (screenName) {
            Flows.Main.name -> TabsFlowFragment.newInstance(data as TabsFlowArgs)
            Flows.Login.name -> LoginFlowFragment.newInstance()
            Flows.Chats.name ->
                ChatFlowFragment.newInstance(data as ChatsFlowScreenArgs)
            Flows.Patients.name -> ScenariosFlowFragment.newInstance()
            Flows.Events.name ->
                DialingsFlowFragment.newInstance(data as EventsFlowScreenArgs)
            Flows.Stats.name ->
                StatsFlowFragment.newInstance()
            Flows.TesterSettings.name ->
                TesterSettingsFlowFragment.newInstance()
            Flows.Game.name -> GameFlowFragment.newInstance()
            else -> null
        }
    }
}