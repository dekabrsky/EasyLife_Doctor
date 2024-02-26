package ru.dekabrsky.italks.basic.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

abstract class FlowNavigator(
    val activity: FragmentActivity,
    private val containerId: Int
) : SupportAppNavigator(activity, containerId) {

    private val fragmentManager get() = activity.supportFragmentManager

    @Suppress("UseIfInsteadOfWhen")
    override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
        return when (screenKey) {
            BaseScreens.SCREEN_OPEN_SETTINGS -> {
                return Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + context.packageName)
                )
            }
            else -> null // base screens
        }

    }

    override fun getRequestCodeForScreen(screenKey: String): Int {
        return when (screenKey) {
            else -> -1
        }
    }

//    protected fun checkForIntentHandlers(activity: FragmentActivity, intent: Intent) {
//        if (androidNavigationUtils.existExternalAppForIntent(intent).not()) {
//            showSystemMessage(activity.getString(R.string.error_no_available_apps))
//        }
//    }

    override fun applyCommand(command: Command?) {
        when (command) {
            is PresetScreen -> preSetFragment(command)
            is ToggleScreen -> toggleFragment(command)
            is NewData -> sendNewData(command)
            is NavigateToStart -> navigateToStart(command)
            is AddScreen -> addScreen(command)
            else -> super.applyCommand(command)
        }
    }

    private fun preSetFragment(command: PresetScreen) {
        if (activity.supportFragmentManager.findFragmentByTag(command.screenKey) == null) {
            val fragment = createFragment(command.screenKey, command.transitionData)
            if (fragment == null) {
                unknownScreen(command)
            } else {
                activity.supportFragmentManager.beginTransaction()
                    .add(containerId, fragment, command.screenKey)
                    .commitNow()
            }
        }
    }

    private fun toggleFragment(toggleScreen: ToggleScreen) {
        val newFragment =
            activity.supportFragmentManager.findFragmentByTag(toggleScreen.newScreenKey)
        val oldFragment =
            activity.supportFragmentManager.findFragmentByTag(toggleScreen.oldScreenKey)

        val transaction = activity.supportFragmentManager.beginTransaction()
        newFragment?.let { fragment ->
            transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
            transaction.show(fragment)
            fragment.userVisibleHint = true
            (fragment as? NewDataListener)?.onNewData(toggleScreen.data)
        }
        oldFragment?.let {
            transaction.setMaxLifecycle(it, Lifecycle.State.STARTED)
            transaction.hide(it)
            it.userVisibleHint = false
        }
        transaction.commit()
    }

    private fun addScreen(command: AddScreen) {
        val activityIntent =
            createActivityIntent(activity, command.screenKey, command.transitionData)
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.screenKey, activityIntent, options)
        } else {
            val fragment = createFragment(command.screenKey, command.transitionData)

            if (fragment == null) {
                unknownScreen(command)
                return
            }

            val fragmentTransaction =
                fragmentManager.beginTransaction().addToBackStack(command.screenKey)

            if (fragment is DialogFragment) {
                fragment.show(fragmentTransaction, command.screenKey)
            } else {
                setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
                )
                fragmentTransaction
                    .add(containerId, fragment, command.screenKey)
                    .commit()
            }

            localStackCopy.add(command.screenKey)
        }
    }

    override fun forward(command: Forward) {
        val activityIntent =
            createActivityIntent(activity, command.screenKey, command.transitionData)
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.screenKey, activityIntent, options)
        } else {
            val fragment = createFragment(command.screenKey, command.transitionData)

            if (fragment == null) {
                unknownScreen(command)
                return
            }

            val fragmentTransaction =
                fragmentManager.beginTransaction().addToBackStack(command.screenKey)

            if (fragment is DialogFragment) {
                fragment.show(fragmentTransaction, command.screenKey)
            } else {
                setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
                )
                fragmentTransaction
                    .replace(containerId, fragment, command.screenKey)
                    .commit()
            }

            localStackCopy.add(command.screenKey)
        }
    }

    override fun replace(command: Replace) {
        val activityIntent =
            createActivityIntent(activity, command.screenKey, command.transitionData)

        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.screenKey, activityIntent, options)
            activity.finish()
        } else {
            val fragment = createFragment(command.screenKey, command.transitionData)

            if (fragment == null) {
                unknownScreen(command)
                return
            }
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (localStackCopy.size > 0) {
                fragmentManager.popBackStack()
                localStackCopy.pop()
                fragmentTransaction.addToBackStack(command.screenKey)

                if (fragment is DialogFragment) {
                    fragment.show(fragmentTransaction, command.screenKey)
                } else {
                    setupFragmentTransactionAnimation(
                        command,
                        fragmentManager.findFragmentById(containerId),
                        fragment,
                        fragmentTransaction
                    )
                    fragmentTransaction
                        .replace(containerId, fragment, command.screenKey)
                        .commit()
                }

                localStackCopy.add(command.screenKey)

            } else {
                fragmentManager.findFragmentByTag(command.screenKey)?.let {
                    fragmentManager.beginTransaction().remove(it).commit()
                }

                if (fragment is DialogFragment) {
                    fragment.show(fragmentTransaction, command.screenKey)
                } else {
                    setupFragmentTransactionAnimation(
                        command,
                        fragmentManager.findFragmentById(containerId),
                        fragment,
                        fragmentTransaction
                    )
                    fragmentTransaction
                        .replace(containerId, fragment, command.screenKey)
                        .commit()
                }
            }
        }
    }

    private fun checkAndStartActivity(screenKey: String, activityIntent: Intent, options: Bundle?) {
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            ActivityCompat.startActivityForResult(
                activity,
                activityIntent,
                getRequestCodeForScreen(screenKey),
                options
            )
        } else {
            unexistingActivity(screenKey, activityIntent)
        }
    }

    private fun sendNewData(newData: NewData) {
        val fragment = activity.supportFragmentManager.findFragmentByTag(newData.screenKey)
        (fragment as? NewDataListener)?.onNewData(newData.transitionData)
    }

    override fun exit() {
        activity.setResult(Activity.RESULT_CANCELED)
        activity.finish()
    }

    protected open fun navigateToStart(command: NavigateToStart) {}

    protected open fun setLaunchScreen(screenKey: String, data: Any? = null) {
        applyCommands(
            arrayOf(
                BackTo(null),
                Replace(screenKey, data)
            )
        )
    }

}

interface NewDataListener {
    fun onNewData(data: Any?)
}
