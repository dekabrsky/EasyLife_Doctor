package ru.dekabrsky.feature.notifications.implementation.presentation.view

import android.os.Bundle
import android.view.View
import main.utils.setBoolVisibility
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.FmtNotificationsBinding
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.presentation.adapter.NotificationsListAdapter
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationsListPresenter
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class NotificationsListFragment: BasicFragment(), NotificationsListView {
    override val layoutRes = R.layout.fmt_notifications
    private val binding by viewBinding(FmtNotificationsBinding::bind)

    private val adapter by lazy {
        NotificationsListAdapter(
            onItemClick = presenter::onNotificationClick,
            onItemDelete = presenter::onNotificationDelete,
            onItemCheckedChanged = presenter::onItemCheckedChanged
        )
    }

    @InjectPresenter
    lateinit var presenter: NotificationsListPresenter

    @ProvidePresenter
    fun providePresenter(): NotificationsListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_NOTIFICATION, scopeName)
            .getInstance(NotificationsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        setNavBarVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = adapter
        binding.toolbar.setTitle(R.string.notifications)
        binding.addNotification.setOnClickListener { presenter.onAddNotificationClick() }
    }
    override fun setChatsList(items: List<NotificationEntity>) {
        adapter.updateItems(items)
    }

    override fun setEmptyLayoutVisibility(isVisible: Boolean) {
        binding.emptyLayout.setBoolVisibility(isVisible)
    }

    fun setNavBarVisibility(isVisible: Boolean) {
        (parentFragment as NotificationFlowFragment).setNavBarVisibility(isVisible)
    }

    override fun setListLoadingVisibility(isVisible: Boolean) {
        binding.loadingText.setBoolVisibility(isVisible)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = NotificationsListFragment()
    }


}