package ru.dekabrsky.easylife.navigation

import androidx.fragment.app.FragmentActivity
import ru.dekabrsky.easylife.R
import ru.dekabrsky.easylife.basic.di.inject
import ru.dekabrsky.easylife.basic.navigation.FlowNavigator
import ru.dekabrsky.easylife.basic.navigation.NavigateToStart
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.scopes.Scopes
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