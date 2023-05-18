package ru.dekabrsky.dialings.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.adapter.ConcurentsListAdapter
import ru.dekabrsky.dialings.presentation.model.ConcurentUiModel
import ru.dekabrsky.italks.basic.di.IntWrapper
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.util.*
import kotlin.math.roundToInt


class DialingDetailsFragment: BasicFragment(), DialingDetailsView {

    override val layoutRes = R.layout.new_fragment_dialing_details

    private lateinit var product: PlainProduct

    @InjectPresenter
    lateinit var presenter: DialingDetailsPresenter

    private val binding by viewBinding(NewFragmentDialingDetailsBinding::bind)

    private val adapter by lazy { ConcurentsListAdapter() }

    @ProvidePresenter
    fun providePresenter(): DialingDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_DIALINGS, scopeName)
            .module { bind(PlainProduct::class.java).toInstance(product) }
            .getInstance(DialingDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        (parentFragment as DialingsFlowFragment).setNavBarVisibility(false)
        binding.toolbar.title = product.name
        binding.toolbar.subtitle = product.city
        binding.concurentsList.adapter = adapter
//        var builder = NotificationCompat.Builder(requireContext(), "23")
//            .setSmallIcon(R.drawable.ic_logo)
//            .setContentTitle("Труба квадратная 20х20х1.5")
//            .setContentText("Цена упала на 7,64 %")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        with(NotificationManagerCompat.from(requireContext())) {
//            // notificationId is a unique int for each notification that you must define
//            notify(23, builder.build())
//        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }


    @Suppress("MagicNumber")
    override fun setupLineChart(series: ValueLineSeries, trendSeries: ValueLineSeries) {
        series.color = ContextCompat.getColor(requireContext(), R.color.cyan_main)
        trendSeries.color = ContextCompat.getColor(requireContext(), R.color.black)

        binding.lineChartLayout.lineChart.addSeries(series)
        //binding.lineChartLayout.lineChart.addSeries(trendSeries)
        binding.lineChartLayout.lineChart.startAnimation()
        binding.lineChartLayout.root.visibility = View.VISIBLE
    }

    override fun setMainData(product: PlainProduct) {
        binding.progress.currentAvg.text = product.avg.roundToInt().toString() + " ₽"
        binding.progress.minValue.text = product.min.roundToInt().toString() + " ₽"
        binding.progress.maxValue.text = product.max.roundToInt().toString() + " ₽"
    }

    override fun setPrecision(precision: Int, delta: Double) {
        binding.precision.currentAvg.text = "$precision ₽"
        binding.precision.minValue.text = String.format("%.2f", delta) + " %"
    }

    override fun setConcurents(concurents: List<ConcurentUiModel>) {
        adapter.updateItems(concurents)
    }

    companion object {
        fun newInstance(product: PlainProduct) = DialingDetailsFragment().apply {
            this.product = product
        }
    }
}