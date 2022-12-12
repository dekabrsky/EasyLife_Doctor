package ru.dekabrsky.dialings.presentation.presenter

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.callersbase_common.domain.interactor.CallersBaseInteractor
import ru.dekabrsky.callersbase_common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.dialings.presentation.mapper.DialingListUiMapper
import ru.dekabrsky.dialings.presentation.view.DialingDetailsView
import ru.dekabrsky.dialings_common.domain.interactor.DialingsInteractor
import ru.dekabrsky.dialings_common.domain.model.DialingStatus
import ru.dekabrsky.dialings_common.presentation.model.DialingUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.italks.basic.di.IntWrapper
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import java.util.*
import javax.inject.Inject

class DialingDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val dialingId: IntWrapper,
    private val callersBaseInteractor: CallersBaseInteractor,
    private val dialingsInteractor: DialingsInteractor,
    private val dialingUiMapper: DialingListUiMapper
) : BasicPresenter<DialingDetailsView>(router) {

    private lateinit var model: DialingUiModel

    override fun onFirstViewAttach() {
        loadDialingDetails()
    }

    private fun loadDialingDetails() {
        dialingsInteractor.getDialingById(dialingId.value)
            .observeOn(RxSchedulers.main())
            .map { dialingUiMapper.map(it) }
            .subscribe(
                {
                    model = it
                    setData()
                },
                { viewState.showError(it) }
            )
            .addFullLifeCycle()
    }

    private fun setData() {
        viewState.setMainData(model)
        viewState.setupProgress(model.progress, model.formattedProgress, getProgressDetails())
        viewState.setupTime(model.startDate, canEdit = model.status == DialingStatus.SCHEDULED)
        if (model.status != DialingStatus.SCHEDULED) {
            viewState.setupPieChart()
            viewState.setupLineChart()
        }
        viewState.setRunNowVisibility(model.status == DialingStatus.SCHEDULED)
    }

    private fun getProgressDetails(): String = model.status.value

    fun onBaseClick() {
        val baseId = model.callersBaseId

        callersBaseInteractor.getCallersBase(baseId)
            .observeOn(RxSchedulers.main())
            .subscribe(
                {
                    router.startFlow(
                        Flows.Chats.name,
                        ChatsFlowScreenArgs(
                            Scopes.SCOPE_FLOW_DIALINGS,
                            Flows.Chats.SCREEN_BASES_DETAILS,
                            it
                        )
                    )
                },
                { viewState.showError(it) }
            )
            .addFullLifeCycle()
    }

    fun onEditTimeClick() {
        viewState.showDatePicker()
    }

    fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        viewState.showTimePicker(Date(year, monthOfYear + 1, dayOfMonth))
    }

    fun onTimeSet(hourOfDay: Int, minute: Int, date: Date) {
        val localDateTime = LocalDateTime.of(date.year, date.month, date.day, hourOfDay, minute)
        viewState.showNewStartDate(
            formatDateTimeToUiDateTime(localDateTime)
        )
    }

    fun onRunNowClick() = viewState.showRunNowDialog()

    fun runNow() {
        dialingsInteractor.startDialing(model.id)
            .observeOn(RxSchedulers.main())
            .subscribe(
                { loadDialingDetails() },
                {
                    loadDialingDetails() // до починки метода
                    //viewState.showError(it, ::load)
                }
            )
            .addFullLifeCycle()
    }

}