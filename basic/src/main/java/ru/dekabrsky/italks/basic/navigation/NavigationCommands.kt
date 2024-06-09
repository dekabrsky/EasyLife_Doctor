package ru.dekabrsky.easylife.basic.navigation

import ru.terrakok.cicerone.commands.Command

class AddScreen(
    val screenKey: String,
    val transitionData: Any?
) : Command

class PresetScreen(
    val screenKey: String,
    val transitionData: Any?
) : Command

class ToggleScreen(
    val newScreenKey: String,
    val oldScreenKey: String,
    val data: Any? = null
) : Command

class NewData(
    val screenKey: String,
    val transitionData: Any?
) : Command

class NavigateToStart(
    val transitionData: Any?
) : Command