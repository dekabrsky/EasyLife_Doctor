package ru.dekabrsky.italks.game.view.cache

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class GameFlowCache @Inject constructor() {
    val isMusicOnSubject : BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
}