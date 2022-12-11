package ru.dekabrsky.italks.basic.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

abstract class FragmentFlowNavigator(
    private val fragment: Fragment,
    private val router: AppRouter,
    private val containerId: Int,
) : FragmentNavigator(fragment.requireContext(), fragment.childFragmentManager, containerId) {

    private val fragmentManager = fragment.childFragmentManager

    override fun createFragment(screenKey: String?, data: Any?): Fragment? {
        return null
    }

    override fun applyCommand(command: Command?) {
        when (command) {
            is PresetScreen -> preSetFragment(command)
            is ToggleScreen -> toggleFragment(command)
            is NewData -> sendNewData(command)
            else -> super.applyCommand(command)
        }
    }

    @Suppress("TooGenericExceptionThrown")
    private fun preSetFragment(command: PresetScreen) {
        if (fragmentManager.findFragmentByTag(command.screenKey) == null) {
            val fragment = createFragment(command.screenKey, command.transitionData)
            if (fragment == null) {
                throw RuntimeException("Can't create a screen for passed screenKey. ${command.screenKey}")
            } else {
                fragmentManager.beginTransaction()
                    .add(containerId, fragment, command.screenKey)
                    .setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                    .hide(fragment)
                    .commitNow()
            }
        }
    }

    private fun toggleFragment(toggleScreen: ToggleScreen) {
        val newFragment = fragmentManager.findFragmentByTag(toggleScreen.newScreenKey)
        val oldFragment = fragmentManager.findFragmentByTag(toggleScreen.oldScreenKey)

        val transaction = fragmentManager.beginTransaction()
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

    override fun replace(command: Replace) {
        val fragment = createFragment(command.getScreenKey(), command.getTransitionData())

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
                    .setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                    .commit()
            }

            localStackCopy.add(command.screenKey)
        } else {
            dismissDialogs()
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
        fragment.userVisibleHint = true
    }

    override fun forward(command: Forward) {
        val fragment = createFragment(command.screenKey, command.transitionData)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(command.screenKey)

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
                .setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                .commit()
        }

        fragment.userVisibleHint = true
        localStackCopy.add(command.screenKey)
    }

    override fun exit() {
        router.back()
    }

    private fun sendNewData(newData: NewData) {
        val fragment = fragmentManager.findFragmentByTag(newData.screenKey)
        (fragment as? NewDataListener)?.onNewData(newData.transitionData)
    }

    private fun dismissDialogs() {
        fragmentManager.fragments.filterIsInstance(DialogFragment::class.java).forEach {
            it.dismissAllowingStateLoss()
        }
    }
}