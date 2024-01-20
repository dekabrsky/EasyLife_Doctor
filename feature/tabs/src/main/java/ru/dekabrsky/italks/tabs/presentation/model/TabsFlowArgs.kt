package ru.dekabrsky.italks.tabs.presentation.model

import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import java.io.Serializable


data class TabsFlowArgs (
    val userType: UserType,
): Serializable