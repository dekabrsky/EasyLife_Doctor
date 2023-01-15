package ru.dekabrsky.italks.basic.navigation

import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment

interface FlowFragmentProvider {
    fun provideFlowFragment(screenName: String?, data: Any?): BasicFlowFragment?
}