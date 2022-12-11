package ru.dekabrsky.italks.navigation

import androidx.fragment.app.FragmentActivity
import ru.dekabrsky.italks.R
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.navigation.FlowNavigator
import ru.dekabrsky.italks.basic.navigation.NavigateToStart
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

abstract class AppFlowNavigator (activity: FragmentActivity, containerId: Int = R.id.flowContainer) :
    FlowNavigator(activity, containerId) {

    init {
        Toothpick.openScope(Scopes.SCOPE_APP_ROOT).inject(this)
    }

    override fun navigateToStart(command: NavigateToStart) {
        setLaunchScreen(Flows.Main.name)
    }

}