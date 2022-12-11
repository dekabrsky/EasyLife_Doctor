package ru.dekabrsky.dialings.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.NewFragmentDialingDetailsBinding
import ru.dekabrsky.dialings.presentation.presenter.DialingDetailsPresenter
import ru.dekabrsky.dialings_common.domain.model.DialingStatus
import ru.dekabrsky.dialings_common.presentation.model.DialingUiModel
import ru.dekabrsky.italks.basic.di.IntWrapper
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import toothpick.Toothpick
import java.util.*


class DialingDetailsFragment: BasicFragment(), DialingDetailsView {

    override val layoutRes = R.layout.new_fragment_dialing_details

    private lateinit var dialingId : IntWrapper

    @InjectPresenter
    lateinit var presenter: DialingDetailsPresenter

    private val binding by viewBinding(NewFragmentDialingDetailsBinding::bind)

    @ProvidePresenter
    fun providePresenter(): DialingDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_DIALINGS, scopeName)
            .module { bind(IntWrapper::class.java).toInstance(dialingId) }
            .getInstance(DialingDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.base.root.setOnClickListener { presenter.onBaseClick() }
        binding.progress.runNow.setOnClickListener { presenter.onRunNowClick() }
        (parentFragment as DialingsFlowFragment).setNavBarVisibility(false)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setMainData(model: DialingUiModel) {
        binding.toolbar.title = model.name

        // base
        binding.base.title.text = "Базы данных"
        binding.base.value.text = model.callersBaseName
        binding.base.subValue.text = "${model.callersBaseCount} эл"
        binding.base.root.visibility = View.VISIBLE

        // scenario
        binding.scenario.title.text = "Сценарий"
        binding.scenario.value.text = model.scenarioName
        binding.scenario.subValue.text = "Шагов: ${model.scenarioStepsCount}"
        binding.scenario.root.visibility = View.VISIBLE

        if (model.status != DialingStatus.SCHEDULED) {
            binding.time.endValue.text = model.endDate
            binding.time.end.visibility = View.VISIBLE
            binding.time.endValue.visibility = View.VISIBLE
            binding.time.root.visibility = View.VISIBLE
        }
    }

    override fun setupProgress(progressInt: Int, progress: String, details: String) {
        binding.progress.classicProgress.progress = progressInt
        binding.progress.progressValue.text = progress
        binding.progress.progressDetails.text = details
        binding.progress.root.visibility = View.VISIBLE
    }

    override fun setupTime(startTime: String, canEdit: Boolean) {
        binding.time.beginValue.text = startTime
        binding.time.root.visibility = View.VISIBLE
        if (canEdit) {
            binding.time.editBegin.visibility = View.VISIBLE
            binding.time.editBegin.setOnClickListener { presenter.onEditTimeClick() }
        }
    }

    override fun setupPieChart() {
        binding.pieChart.piechart.addPieSlice(
            PieModel(
                getString(R.string.success_finished),
                70F,
                ContextCompat.getColor(requireContext(), R.color.green_600)
            )
        )
        binding.pieChart.piechart.addPieSlice(
            PieModel(
                getString(R.string.scenario_not_passed),
                8F,
                ContextCompat.getColor(requireContext(), R.color.red_800)
            )
        )
        binding.pieChart.piechart.addPieSlice(
            PieModel(
                getString(R.string.not_ringing),
                14F,
                ContextCompat.getColor(requireContext(), R.color.grey_600)
            )
        )
        binding.pieChart.piechart.addPieSlice(
            PieModel(
                getString(R.string.in_progress),
                14F,
                ContextCompat.getColor(requireContext(), R.color.grey_200)
            )
        )
        binding.pieChart.root.visibility = View.VISIBLE
        binding.pieChart.piechart.startAnimation()
    }

    override fun setupLineChart() {
        val series = ValueLineSeries()
        series.color = ContextCompat.getColor(requireContext(), R.color.orange_600)

        series.addPoint(ValueLinePoint("12:00", 34f))
        series.addPoint(ValueLinePoint("13:00", 24f))
        series.addPoint(ValueLinePoint("14:00", 36f))
        series.addPoint(ValueLinePoint("15:00", 14f))
        series.addPoint(ValueLinePoint("16:00", 38f))
        series.addPoint(ValueLinePoint("17:00", 54f))
        series.addPoint(ValueLinePoint("18:00", 39f))

        binding.lineChartLayout.lineChart.addSeries(series)
        binding.lineChartLayout.lineChart.startAnimation()
        binding.lineChartLayout.root.visibility = View.VISIBLE
    }

    override fun showDatePicker() {
        val dpd: DatePickerDialog =
            DatePickerDialog.newInstance(null, Calendar.getInstance())

        dpd.setOkText("Далее")
        dpd.setCancelText("Отмена")
        dpd.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            presenter.onDateSet(year, monthOfYear, dayOfMonth)
        }

        dpd.show(requireActivity().supportFragmentManager, DATE_PICKER_TAG)
    }

    override fun showTimePicker(date: Date) {
        val tpd: TimePickerDialog = TimePickerDialog.newInstance(null, true)

        tpd.setOkText("Ок")
        tpd.setCancelText("Отмена")
        tpd.setOnTimeSetListener { _, hourOfDay, minute, _ ->
            presenter.onTimeSet(hourOfDay, minute, date)
        }

        tpd.show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
    }

    override fun showNewStartDate(newValue: String) {
        binding.time.beginValue.text = newValue
    }

    override fun setRunNowVisibility(isVisible: Boolean) {
        binding.progress.runNow.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showRunNowDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Запустить сейчас?")
        builder.setPositiveButton("Да") { _, _ -> presenter.runNow() }
        builder.setNegativeButton("Нет") { _, _ -> }
        builder.show()
    }

    companion object {
        private const val DATE_PICKER_TAG = "DATE_PICKER_DIALOG"
        private const val TIME_PICKER_TAG = "TIME_PICKER_DIALOG"

        fun newInstance(dialingId: Int) = DialingDetailsFragment().apply {
            this.dialingId = IntWrapper(dialingId)
        }
    }
}