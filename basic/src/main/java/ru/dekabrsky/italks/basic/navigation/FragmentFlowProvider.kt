package ru.dekabrsky.italks.basic.navigation

import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.fragments.BasicFragment

interface FlowFragmentProvider {
    fun provideFlowFragment(screenName: String?, data: Any?): BasicFlowFragment?
}