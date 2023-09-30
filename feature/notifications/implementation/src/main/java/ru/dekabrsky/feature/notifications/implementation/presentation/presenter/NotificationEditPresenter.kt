package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import android.view.View
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.domain.interactor.NotificationInteractor
import ru.dekabrsky.feature.notifications.implementation.presentation.mapper.NotificationEntityToUiMapper
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationEditView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import javax.inject.Inject

class NotificationEditPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: NotificationInteractor,
    private val existingNotification: NotificationEntity,
    private val mapper: NotificationEntityToUiMapper
): BasicPresenter<NotificationEditView>(router) {

    private var notification = existingNotification.uid?.let {
        mapper.mapEntityToUi(existingNotification)
    } ?: NotificationEditUiModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        notification.let {
            viewState.setNotesFields(it)
            onTimeSet(it.hour, it.minute)
        }
    }

    fun onTabletNameChanged(tabletName: String) {
        notification.tabletName = tabletName
    }

    fun onDosageChanged(dosage: String) {
        notification.dosage = dosage
    }

    fun onNoteChanged(note: String) {
        notification.note = note
    }

    fun onTimeClick() {
        viewState.showTimePicker(notification.hour, notification.minute)
    }

    fun onDoneClick() {
        val result = mapper.mapUiToEntity(notification, existingNotification.uid)

        val saveMethod = if (existingNotification.uid == null) {
            interactor.add(result)
        } else {
            interactor.update(result)
        }

       saveMethod
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