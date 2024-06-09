package ru.dekabrsky.easylife.basic.navigation

import ru.dekabrsky.easylife.basic.fragments.BasicFlowFragment

interface FlowFragmentProvider {
    fun provideFlowFragment(screenName: String?, data: Any?): BasicFlowFragment?
}