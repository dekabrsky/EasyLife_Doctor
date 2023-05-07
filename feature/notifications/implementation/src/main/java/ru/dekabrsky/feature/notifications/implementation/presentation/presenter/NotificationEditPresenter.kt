package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import android.view.View
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationEditView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import javax.inject.Inject

class NotificationEditPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: NotificationInteractor
): BasicPresenter<NotificationEditView>(router) {

    private var notification = NotificationEditUiModel()
    fun onTabletNameChanged(tabletName: String) {
        notification.tabletName = tabletName
    }

    fun onDosageChanged(dosage: String) {
        notification.dosage = dosage
    }

    fun onNoteChanged(note: String) {
        notification.note = note
    }

    fun onTimeClick(view: View?) {
        viewState.showTimePicker()
    }

    fun onDoneClick(view: View?) {
        val result = NotificationEntity(
            tabletName = notification.tabletName,
            dosage = notification.dosage,
            note = notification.note,
            hour = notification.hour,
            minute = notification.minute
        )
        interactor.add(result)
            .observeOn(RxSchedulers.main())
            .subscribe ({ onBackPressed() }, {viewState.showError(it) })
            .addFullLifeCycle()
    }

    fun onTimeSet(hourOfDay: Int, minute: Int) {
        notification.hour = hourOfDay
        notification.minute = minute
        viewState.setTime(notification.time)
    }
}