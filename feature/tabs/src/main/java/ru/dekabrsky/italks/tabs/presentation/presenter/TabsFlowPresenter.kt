package ru.dekabrsky.italks.tabs.presentation.presenter

import ru.dekabrsky.common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.italks.basic.navigation.Flow
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.feature.tabs.R
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.italks.tabs.presentation.view.TabsFlowView
import javax.inject.Inject

class TabsFlowPresenter @Inject constructor(
    private val args: TabsFlowArgs,
    private val router: FlowRouter,
    private val loginDataCache: LoginDataCache
) : BasicPresenter<TabsFlowView>(router) {

    private var currentFlow: Flow? = null
    private val screensChain by lazy { LinkedHashSet<Flow>() }

    override fun onFirstViewAttach() {

        viewState.setTabsByRole(
            when (args.userType) {
                UserType.DOCTOR -> R.menu.doctor_tabs_menu
                UserType.PATIENT -> R.menu.patient_tabs_menu
                UserType.PARENT -> R.menu.parent_tabs_menu
                UserType.CHILD -> R.menu.child_tabs_menu
            }
        )

        router.preSetScreens(
            Flows.Chats.name to ChatsFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.Chats.SCREEN_CHATS_LIST
            ),
            Flows.Patients.name to null,
            Flows.Events.name to EventsFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.Events.SCREEN_DIALINGS_LIST
            ),
            Flows.Stats.name to null,
            Flows.Materials.name to null,
            Flows.Game.name to null,
            Flows.Profile.name to null,
            Flows.Notifications.name to NotificationsFlowArgs(Scopes.SCOPE_APP)
        )
        when (args.userType) {
            UserType.DOCTOR -> setDoctorStartScreen()
            UserType.PATIENT -> setPatientStartScreen()
            UserType.PARENT -> toggleScreen(Flows.Chats)
            UserType.CHILD -> setChildStartScreen()
        }
    }

    private fun setDoctorStartScreen() {
        toggleScreen(
            when {
                loginDataCache.registeredPatientId != null -> Flows.Patients
                loginDataCache.patientMedicinesDiff != null -> Flows.Patients
                loginDataCache.chatId != null -> Flows.Chats
                else -> Flows.Patients
            }
        )
    }

    private fun setPatientStartScreen() {
        toggleScreen(
            when {
                loginDataCache.medReminder != null -> Flows.Game
                loginDataCache.medicinesDiff != null -> Flows.Notifications
                loginDataCache.chatId != null -> Flows.Chats
                else -> Flows.Game
            }
        )
    }

    private fun setChildStartScreen() {
        toggleScreen(
            when {
                loginDataCache.medReminder != null -> Flows.Game
                loginDataCache.medicinesDiff != null -> Flows.Notifications
                else -> Flows.Game
            }
        )
    }

    fun onTabSelect(itemId: Int) {
        when (itemId) {
            R.id.chats -> toggleScreen(
                Flows.Chats,
                ChatsFlowScreenArgs(
                    Scopes.SCOPE_APP,
                    Flows.Chats.SCREEN_CHATS_LIST
                )
            )

            R.id.events -> toggleScreen(
                Flows.Events,
                EventsFlowScreenArgs(
                    Scopes.SCOPE_APP,
                    Flows.Events.SCREEN_DIALINGS_LIST
                )
            )

            R.id.notifications -> toggleScreen(Flows.Notifications, NotificationsFlowArgs(Scopes.SCOPE_APP))
            R.id.patients -> toggleScreen(Flows.Patients)
            R.id.stats -> toggleScreen(Flows.Stats)
            R.id.game -> toggleScreen(Flows.Game)
            R.id.materials -> toggleScreen(Flows.Materials)
            R.id.patientProfile -> toggleScreen(Flows.Profile)
        }
    }

    private fun selectTab(flow: Flow) {
        when (flow) {
            Flows.Events -> viewState.setSelectedCallTab()
            Flows.Patients -> viewState.setSelectedScenariosTab()
        }
    }

    private fun toggleScreen(flow: Flow, data: Any? = null) {
        if (currentFlow != flow) {
            router.toggleScreen(flow.name, currentFlow?.name.orEmpty(), data)
            updateScreensChain(currentFlow, flow)
            currentFlow = flow
        } else {
            router.sendNewData(flow.name, data)
        }
    }

    private fun updateScreensChain(prevScreen: Flow?, nextScreen: Flow) {
        prevScreen ?: return
        screensChain.add(prevScreen)
        if (screensChain.contains(nextScreen)) screensChain.remove(nextScreen)
    }
}