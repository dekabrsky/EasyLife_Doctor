package ru.dekabrsky.scenarios_common.domain.interactor

import io.reactivex.Observable
import io.reactivex.Single
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.scenarios_common.domain.model.ScenarioEntity

interface DoctorPatientsInteractor {
    fun generateCode(): Single<Int>
}