package ru.dekabrsky.stats.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.domain.model.UserInfoEntity
import ru.dekabrsky.stats.R
import ru.dekabrsky.stats.databinding.FragmentStatsBinding
import ru.dekabrsky.stats.presentation.model.ChartPointUiModel
import ru.dekabrsky.stats.presentation.model.MainStatsUiModel
import ru.dekabrsky.stats.presentation.presenter.StatsPresenter
import toothpick.Toothpick

class StatsFragment: BasicFragment(), StatsView {

    override val layoutRes: Int
        get() = R.layout.fragment_stats

    @InjectPresenter
    lateinit var presenter: StatsPresenter

    private val binding by viewBinding(FragmentStatsBinding::bind)

    @ProvidePresenter
    fun providePresenter(): StatsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_STATS, scopeName)
            .getInstance(StatsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.settings)
        //binding.dialingsCount.title.setText(R.string.dialingsCount)

        binding.myProfile.title.text = "Моя учетная запись"
        binding.myProfile.value.text = "Тест Тестов"
        binding.myProfile.speciality.text = "Директор ТестТЗ"
        Glide
            .with(requireContext())
            .asBitmap()
            .load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Fru%2Ffree-icon%2Fexample_5579119&psig=AOvVaw3knxYNdiIz-80TDywtngZL&ust=1684502289667000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLi3kZj6_v4CFQAAAAAdAAAAABAE")
            .circleCrop()
            .into(binding.myProfile.imageEnd)

        binding.pushSettings.title.text = "push-уведомления"
        binding.pushSettings.value.text = "Активно"
        binding.pushSettings.speciality.text = "При изменении 5%"

        binding.supportCard.title.text = "сообщения"
        binding.supportCard.value.text = "Чат с поддержкой"
        binding.supportCard.speciality.text = "Нет новых сообщений"

        binding.averageDialCount.title.setText(R.string.averageDialCount)
        binding.averageSingleDialLength.title.setText(R.string.averageSingleDialLength)
    }

    override fun showMainStats(model: MainStatsUiModel) {
        //binding.dialingsCount.value.text = model.totalDialings
        //binding.averageDialLength.value.text = model.averageDialingsDuration
        binding.averageDialCount.value.text = model.averageNumberOfCallsPerDialing
        binding.averageSingleDialLength.value.text = model.averageCallDuration
    }

    override fun showLineChart(mapLineChart: List<ChartPointUiModel>) {
        val series = ValueLineSeries()
        series.color = ContextCompat.getColor(requireContext(), R.color.cyan_main)

        mapLineChart.forEach { series.addPoint(ValueLinePoint(it.x, it.y.toFloat())) }

        binding.lineChartLayout.lineChart.addSeries(series)
        binding.lineChartLayout.lineChart.startAnimation()
    }

    @Suppress("MagicNumber")
    override fun showPieChart() {
        binding.pieChartLayout.piechart.addPieSlice(
            PieModel(
                getString(R.string.success_finished),
                75F,
                ContextCompat.getColor(requireContext(), R.color.green_600)
            )
        )
        binding.pieChartLayout.piechart.addPieSlice(
            PieModel(
                getString(R.string.scenario_not_passed),
                15F,
                ContextCompat.getColor(requireContext(), R.color.red_800)
            )
        )
        binding.pieChartLayout.piechart.addPieSlice(
            PieModel(
                getString(R.string.not_ringing),
                10F,
                ContextCompat.getColor(requireContext(), R.color.grey_600)
            )
        )
        binding.pieChartLayout.piechart.addPieSlice(
            PieModel(
                getString(R.string.in_progress),
                0F,
                ContextCompat.getColor(requireContext(), R.color.grey_200)
            )
        )
        binding.pieChartLayout.piechart.startAnimation()
    }

    override fun showMyInfo(infoEntity: UserInfoEntity?) {
         infoEntity?.let {
             binding.myProfile.value.text = it.name
             binding.myProfile.speciality.text = it.role.desc
         }
    }

    companion object{
        fun newInstance() = StatsFragment()
    }
}