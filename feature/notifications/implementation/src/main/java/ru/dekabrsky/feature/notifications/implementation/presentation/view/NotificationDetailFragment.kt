package ru.dekabrsky.feature.notifications.implementation.presentation.view

import ru.dekabrsky.italks.basic.fragments.BasicFragment

class NotificationDetailFragment: BasicFragment(), NotificationDetailView {
    override val layoutRes: Int
        get() = TODO("Not yet implemented")

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = NotificationDetailFragment()
    }
}