package ru.dekabrsky.callersbase.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase_common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.callersbase.presentation.presenter.ChatFlowPresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes.SCOPE_FLOW_CHATS
import toothpick.Toothpick
import javax.inject.Inject

class ChatFlowFragment : BasicFlowFragment(), ChatFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = SCOPE_FLOW_CHATS

    private lateinit var args: ChatsFlowScreenArgs

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Chats.SCREEN_BASES_LIST -> ChatsListFragment.newInstance()
                    Flows.Chats.SCREEN_BASES_DETAILS ->
                        ChatDetailsFragment.newInstance(data as CallersBaseEntity)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: ChatFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(args.parentScope, scopeName)
            .module { bind(ChatsFlowScreenArgs::class.java).toInstance(args) }
            .installNavigation()
            .inject(this)
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        Toothpick.closeScope(scopeName)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance(args: ChatsFlowScreenArgs) =
            ChatFlowFragment().apply { this.args = args }
    }
}