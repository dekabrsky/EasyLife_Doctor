package ru.dekabrsky.easylife.basic.navigation

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

abstract class FragmentNavigator(
    private val context: Context,
    fragmentManager: FragmentManager,
    containerId: Int
) : SupportFragmentNavigatorX(fragmentManager, containerId) {

    fun setLaunchScreen(screenKey: String, data: Any? = null) {
        applyCommands(
            arrayOf(
                BackTo(null),
                Replace(screenKey, data)
            )
        )
    }

    override fun showSystemMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}