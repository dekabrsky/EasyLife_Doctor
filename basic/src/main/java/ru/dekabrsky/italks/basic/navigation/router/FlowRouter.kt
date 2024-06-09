package ru.dekabrsky.easylife.basic.navigation.router

import ru.terrakok.cicerone.result.ResultListener

class FlowRouter constructor(private val appRouter: AppRouter? = null) : AppRouter() {

    override fun setResultListener(resultCode: Int, listener: ResultListener?) {
        super.setResultListener(resultCode, listener)
        appRouter?.setResultListener(resultCode, listener)
    }

    override fun removeResultListener(resultCode: Int) {
        super.removeResultListener(resultCode)
        appRouter?.removeResultListener(resultCode)
    }

    override fun navigateToStart(data: Any?) {
        appRouter?.navigateToStart(data)
            ?: super.navigateToStart(data)
    }

    fun sendGlobalResult(resultCode: Int, result: Any? = null) {
        appRouter?.sendResult(resultCode, result)
            ?: super.sendResult(resultCode, result)
    }

    fun newRootFlow(flowKey: String, data: Any? = null) {
        appRouter?.newRootScreen(flowKey, data)
            ?: super.newRootScreen(flowKey, data)
    }

    fun toggleFlow(newFlowKey: String, oldFlowKey: String, data: Any? = null) {
        appRouter?.toggleScreen(newFlowKey, oldFlowKey, data)
            ?: super.toggleScreen(newFlowKey, oldFlowKey, data)
    }

    fun startFlows(chain: ScreenChain) {
        chain.screens.forEach {
            appRouter?.navigateTo(it.name, it.data) ?: super.navigateTo(it.name, it.data)
        }
    }

    fun replaceFlow(flowKey: String, data: Any? = null) {
        appRouter?.replaceScreen(flowKey, data)
            ?: super.replaceScreen(flowKey, data)
    }

    fun startFlow(flowKey: String, data: Any? = null) {
        appRouter?.navigateTo(flowKey, data)
            ?: super.navigateTo(flowKey, data)
    }

    fun addFlow(flowKey: String, data: Any? = null) {
        appRouter?.addScreen(flowKey, data)
            ?: super.addScreen(flowKey, data)
    }

    fun finishFlow(data: Any? = null, resultCode: Int = REQUEST_CODE_INVALID) {
        if (resultCode != REQUEST_CODE_INVALID) {
            appRouter?.exitWithResult(resultCode, data) ?: super.exitWithResult(resultCode, data)
        } else {
            appRouter?.exit() ?: super.exit()
        }
    }

    fun backToFlow(screenKey: String? = null) {
        appRouter?.backTo(screenKey) ?: super.backTo(screenKey)
    }

    companion object {
        private const val REQUEST_CODE_INVALID = -1
    }

    override fun sendNewData(screenKey: String, data: Any?) {
        super.sendNewData(screenKey, data)
        appRouter?.sendNewData(screenKey, data)
    }
}