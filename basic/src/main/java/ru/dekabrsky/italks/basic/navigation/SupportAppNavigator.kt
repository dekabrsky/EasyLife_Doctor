package ru.dekabrsky.easylife.basic.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

abstract class SupportAppNavigator(private val activity: FragmentActivity, containerId: Int)
    : SupportFragmentNavigatorX(activity.supportFragmentManager, containerId) {

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only [Forward] or [Replace]
     * @param activityIntent activity intent
     * @return transition options
     */
    protected open fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? = null

    override fun forward(command: Forward) {
        val activityIntent = createActivityIntent(activity, command.screenKey, command.transitionData)

        // Start activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.screenKey, activityIntent, options)
        } else {
            super.forward(command)
        }
    }

    override fun replace(command: Replace) {
        val activityIntent = createActivityIntent(activity, command.screenKey, command.transitionData)

        // Replace activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.screenKey, activityIntent, options)
            activity.finish()
        } else {
            super.replace(command)
        }
    }

    private fun checkAndStartActivity(screenKey: String, activityIntent: Intent, options: Bundle?) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            ActivityCompat.startActivityForResult(activity, activityIntent, getRequestCodeForScreen(screenKey), options)
        } else {
            unexistingActivity(screenKey, activityIntent)
        }
    }

    protected open fun getRequestCodeForScreen(screenKey: String) = 0

    /**
     * Called when there is no activity to open `screenKey`.
     *
     * @param screenKey screen key
     * @param activityIntent intent passed to start Activity for the `screenKey`
     */
    @Suppress("EmptyFunctionBlock")
    protected fun unexistingActivity(screenKey: String, activityIntent: Intent) {}

    /**
     * Creates Intent to start Activity for `screenKey`.
     *
     *
     * **Warning:** This method does not work with [BackTo] command.
     *
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key
     */
    protected abstract fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent?

    override fun showSystemMessage(message: String) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun exit() {
        // Finish by default
        activity.finish()
    }
}
