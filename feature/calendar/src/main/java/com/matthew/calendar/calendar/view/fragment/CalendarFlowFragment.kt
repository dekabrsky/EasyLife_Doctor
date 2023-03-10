package com.matthew.calendar.calendar.view.fragment

import androidx.fragment.app.Fragment
import com.matthew.calendar.calendar.view.CalendarFlowView
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.flows.R
import javax.inject.Inject

class CalendarFlowFragment: BasicFlowFragment(), CalendarFlowView {
    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    @Inject
    lateinit var flowFragmentProvider: FlowFragmentProvider

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                if (screenKey == Flows.Profile.SCREEN_PROFILE) {
                    CalendarFragment.newInstance()
                } else {
                    super.createFragment(screenKey, data)
                }
        }
}