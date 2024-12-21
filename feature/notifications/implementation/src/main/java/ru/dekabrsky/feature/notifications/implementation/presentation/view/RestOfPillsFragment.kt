package ru.dekabrsky.feature.notifications.implementation.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.FmtRestOfPillsListBinding
import ru.dekabrsky.feature.notifications.implementation.presentation.adapter.MedicineAdapter
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationEditPresenter
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.RestOfPillsPresenter
import toothpick.Toothpick

class RestOfPillsFragment(
    private val notificationsScope: String
): BasicFragment(), RestOfPillsView {

    override val layoutRes = R.layout.fmt_rest_of_pills_list

    private val binding by viewBinding(FmtRestOfPillsListBinding::bind)

    @InjectPresenter
    lateinit var presenter: RestOfPillsPresenter

    //private val adapter by lazy { MedicineAdapter(presenter) }

    @ProvidePresenter
    fun providePresenter(): RestOfPillsPresenter {
        return Toothpick.openScopes(notificationsScope, scopeName)
            .getInstance(RestOfPillsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.toolbar.setTitle(R.string.rest_of_pills)

        (parentFragment as NotificationFlowFragment).setNavBarVisibility(false)

        binding.addPill.setOnClickListener { presenter.onAddPilClick() }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {

        fun newInstance(
            notificationsScope: String
        ) = RestOfPillsFragment(notificationsScope)
    }
}