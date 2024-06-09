package ru.dekabrsky.easylife.basic.navigation.router

import ru.dekabrsky.easylife.basic.navigation.AddScreen
import ru.dekabrsky.easylife.basic.navigation.NavigateToStart
import ru.dekabrsky.easylife.basic.navigation.NewData
import ru.dekabrsky.easylife.basic.navigation.PresetScreen
import ru.dekabrsky.easylife.basic.navigation.ToggleScreen
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Forward
import java.io.Serializable

@Suppress("SpreadOperator", "ArrayPrimitive")
abstract class AppRouter : Router() {

    fun sendResult(resultCode: Int): Boolean {
        return super.sendResult(resultCode, null)
    }

    public override fun sendResult(resultCode: Int, result: Any?): Boolean {
        return super.sendResult(resultCode, result)
    }

    fun backWithResult(resultCode: Int, result: Any? = null) {
        back()
        sendResult(resultCode, result)
    }

    fun back() {
        executeCommands(Back())
    }

    fun preSetScreens(vararg screenKeys: String) {
        preSetScreens(*screenKeys.map { Pair(it, null) }.toTypedArray())
    }

    fun preSetScreens(vararg pairs: Pair<String, Any?>) {
        executeCommands(*pairs.map { PresetScreen(it.first, it.second) }.toTypedArray())
    }

    fun toggleScreen(newScreenKey: String, oldScreenKey: String, data: Any? = null) {
        executeCommands(ToggleScreen(newScreenKey, oldScreenKey, data))
    }

    fun addScreen(screenKey: String, data: Any? = null) {
        executeCommands(AddScreen(screenKey, data))
    }

    open fun navigateToStart(data: Any? = null) {
        executeCommands(NavigateToStart(data))
    }

    fun openScreenChain(chain: List<Screen>) {
        executeCommands(*chain.map { Forward(it.name, it.data) }.toTypedArray())
    }

    open fun sendNewData(screenKey: String, data: Any? = null) {
        executeCommands(NewData(screenKey, data))
    }

    data class Screen(val name: String, val data: Any? = null) : Serializable
    data class ScreenChain(val screens: List<Screen>) : Serializable
}