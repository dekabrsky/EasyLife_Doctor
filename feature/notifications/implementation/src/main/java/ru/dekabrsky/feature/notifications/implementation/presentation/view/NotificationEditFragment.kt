package ru.dekabrsky.feature.notifications.implementation.presentation.view

import android.os.Bundle
import android.view.View
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import main.utils.onTextChange
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.FmtNotificationEditBinding
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationEditPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.Date

class NotificationEditFragment: BasicFragment(), NotificationEditView {
    override val layoutRes = R.layout.fmt_notification_edit
    private val binding by viewBinding(FmtNotificationEditBinding::bind)

    @InjectPresenter
    lateinit var presenter: NotificationEditPresenter

    @ProvidePresenter
    fun providePresenter(): NotificationEditPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_NOTIFICATION, scopeName)
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
        binding.notificationTimeContainer.setOnClickListener(presenter::onTimeClick)
        binding.doneBtn.setOnClickListener(presenter::onDoneClick)
        (parentFragment as NotificationFlowFragment).setNavBarVisibility(false)
    }

    override fun showTimePicker() {
        val tpd: TimePickerDialog = TimePickerDialog.newInstance(null, true)

        tpd.setOkText("Ок")
        tpd.setCancelText("Отмена")
        tpd.setOnTimeSetListener { _, hourOfDay, minute, _ ->
            presenter.onTimeSet(hourOfDay, minute)
        }

        tpd.show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
    }

    override fun setTime(time: String) {
        binding.notificationTime.text = time
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        private const val TIME_PICKER_TAG = "TIME_PICKER_DIALOG"
        fun newInstance() = NotificationEditFragment()
    }
}