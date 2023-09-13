package ru.dekabrsky.italks.flows

import ru.dekabrsky.italks.basic.navigation.Flow

class Flows {

    object Main : Flow("FLOW_MAIN")

    object Login : Flow("FLOW_LOGIN") {
        const val SCREEN_LOGIN = "SCREEN_LOGIN"
    }

    object Chats : Flow("FLOW_CALLERS_BASE") {
        const val SCREEN_BASES_LIST = "SCREEN_BASES_LIST"
        const val SCREEN_CHAT_CONVERSATION = "SCREEN_BASES_DETAILS"
    }

    object Notifications: Flow("FLOW_NOTIFICATIONS") {
        const val SCREEN_NOTIFICATIONS_LIST = "SCREEN_NOTIFICATIONS_LIST"
        const val SCREEN_EDIT_NOTIFICATION = "SCREEN_EDIT_NOTIFICATION"
    }

    object Materials : Flow("FLOW_MATERIALS") {
        const val SCREEN_MATERIALS_LIST = "SCREEN_MATERIALS_LIST"
        const val SCREEN_MATERIAL_DETAILS = "SCREEN_MATERIAL_DETAILS"
    }

    object Events : Flow("FLOW_DIALING") {
        const val SCREEN_DIALINGS_LIST = "SCREEN_DIALINGS_LIST"
        const val SCREEN_DIALING_DETAILS = "SCREEN_DIALING_DETAILS"
    }

    object Patients : Flow("FLOW_SCENARIOS") {
        const val SCREEN_SCENARIOS_LIST = "SCREEN_SCENARIOS_LIST"
        const val SCREEN_SCENARIO_DETAILS = "SCREEN_SCENARIO_DETAILS"
    }

    object Stats : Flow("FLOW_STATS") {
        const val SCREEN_MAIN_STATS = "SCREEN_MAIN_STATS"
    }

    object TesterSettings : Flow("FLOW_TESTER_SETTINGS") {
        const val SCREEN_TESTER_SETTINGS = "SCREEN_TESTER_SETTINGS"
    }

    object Game : Flow("FLOW_GAME") {
        const val SCREEN_START_GAME = "SCREEN_GABE"
        const val SCREEN_MAIN_ROOM = "SCREEN_MAIN_ROOM"
        const val SCREEN_GARDEN = "SCREEN_GARDEN"
        const val SCREEN_FOOTBALL = "SCREEN_FOOTBALL"
        const val SCREEN_LEAVES = "SCREEN_LEAVES"
        const val SCREEN_FIFTEEN = "SCREEN_FIFTEEN"
    }

    object Profile : Flow("FLOW_PROFILE") {
        const val SCREEN_PROFILE = "SCREEN_PROFILE"
    }

    object Calendar : Flow("FLOW_CALENDAR") {
        const val SCREEN_CALENDAR = "SCREEN_CALENDAR"
    }

    object Common : Flow("COMMON") {
        const val SCREEN_BOTTOM_INFO = "SCREEN_BOTTOM_INFO"
    }
}