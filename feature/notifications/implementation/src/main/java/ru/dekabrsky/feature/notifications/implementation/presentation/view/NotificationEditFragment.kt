package ru.dekabrsky.feature.notifications.implementation.presentation.view

import android.os.Bundle
import android.view.View
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import main.utils.onTextChange
import main.utils.setBoolVisibility
import main.utils.setIsCheckedWithoutEffects
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.threeten.bp.LocalDate
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.FmtNotificationEditBinding
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationEditPresenter
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class NotificationEditFragment: BasicFragment(), NotificationEditView {
    override val layoutRes = R.layout.fmt_notification_edit
    private val binding by viewBinding(FmtNotificationEditBinding::bind)

    private var notification = NotificationEntity()

    @InjectPresenter
    lateinit var presenter: NotificationEditPresenter

    @ProvidePresenter
    fun providePresenter(): NotificationEditPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_NOTIFICATION, scopeName)
            .module { bind(NotificationEntity::class.java).toInstance(notification) }
            .getInstance(NotificationEditPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.toolbar.setTitle(R.string.notification)

        binding.tabletName.onTextChange(presenter::onTabletNameChanged)
        binding.tabletDosage.onTextChange(presenter::onDosageChanged)
        binding.tabletNote.onTextChange(presenter::onNoteChanged)

        binding.notificationTimeContainer.setOnClickListener { presenter.onTimeClick() }
        binding.startDateContainer.setOnClickListener { presenter.onStartDateClick() }
        binding.endDateContainer.setOnClickListener { presenter.onEndDateClick() }

        binding.doneBtn.setOnClickListener { presenter.onDoneClick() }

        binding.weekDaysPicker.setOnWeekdaysChangeListener { _, _, selectedDays ->
            presenter.onCheckedDaysChanged(selectedDays)
        }

        binding.durationSwitch.setOnCheckedChangeListener { _, isChecked -> presenter.onDurationCheckedChanged(isChecked) }

        binding.enabledSwitch.setOnCheckedChangeListener { _, isChecked -> presenter.onEnabledCheckedChanged(isChecked) }

        (parentFragment as NotificationFlowFragment).setNavBarVisibility(false)
    }

    override fun showTimePicker(hour: Int, minute: Int) {
        val tpd: TimePickerDialog = TimePickerDialog.newInstance(null, hour, minute, true)

        tpd.setOkText("Ок")
        tpd.setCancelText("Отмена")
        tpd.setOnTimeSetListener { _, hourOfDay, minute, _ ->
            presenter.onTimeSet(hourOfDay, minute)
        }

        tpd.show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
    }

    override fun showStartDatePicker(date: LocalDate) =
        showDatePicker(date, presenter::onStartDateSet, START_DATE_PICKER_TAG)

    override fun showEndDatePicker(date: LocalDate) =
        showDatePicker(date, presenter::onEndDateSet, END_DATE_PICKER_TAG)

    override fun setStartDate(startDateString: String) {
        binding.startDate.text = startDateString
    }

    override fun setEndDate(endDateString: String) {
        binding.endDate.text = endDateString
    }

    override fun setDurationFieldsVisibility(checked: Boolean) {
        binding.durationLayout.setBoolVisibility(checked)
    }

    override fun setDurationSwitchIsChecked(checked: Boolean) {
        binding.durationSwitch.setIsCheckedWithoutEffects(checked, presenter::onDurationCheckedChanged)
    }

    override fun setNotificationEnabled(enabled: Boolean) {
        binding.enabledSwitch.setIsCheckedWithoutEffects(enabled, presenter::onEnabledCheckedChanged)
    }

    private fun showDatePicker(date: LocalDate, callback: (LocalDate) -> Unit, tag: String) {
        val dpd: DatePickerDialog = DatePickerDialog.newInstance(null, date.year, date.monthValue - 1, date.dayOfMonth)

        dpd.setOkText("Ок")
        dpd.setCancelText("Отмена")
        dpd.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            callback.invoke(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        }

        dpd.show(requireActivity().supportFragmentManager, tag)
    }

    override fun setTime(time: String) {
        binding.notificationTime.text = time
    }

    override fun setNotesFields(uiModel: NotificationEditUiModel) {
        binding.tabletName.setText(uiModel.tabletName)
        binding.tabletDosage.setText(uiModel.dosage)
        binding.tabletNote.setText(uiModel.note)
    }

    override fun setSelectedDays(selectedDays: List<Int>) {
        binding.weekDaysPicker.selectedDays = selectedDays
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        private const val TIME_PICKER_TAG = "TIME_PICKER_DIALOG"
        private const val START_DATE_PICKER_TAG = "START_DATE_PICKER_TAG"
        private const val END_DATE_PICKER_TAG = "END_DATE_PICKER_TAG"
        fun newInstance(notification: NotificationEntity) = NotificationEditFragment().apply {
            this.notification = notification
        }
    }
}