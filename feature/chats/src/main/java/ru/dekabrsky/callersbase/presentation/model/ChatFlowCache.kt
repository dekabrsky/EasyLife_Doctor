package ru.dekabrsky.callersbase.presentation.model

import javax.inject.Inject

class ChatFlowCache @Inject constructor() {
    var existingCompanionIds: List<Int> = listOf()
}