package ru.dekabrsky.italks.basic.navigation.di

import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject
import javax.inject.Provider

class LocalRouterProvider @Inject constructor(private val router: AppRouter) :
    Provider<FlowRouter> {
    override fun get(): FlowRouter = FlowRouter(router)
}

class LocalCiceroneProvider @Inject constructor(private val router: FlowRouter) :
    Provider<Cicerone<FlowRouter>> {
    override fun get(): Cicerone<FlowRouter> = Cicerone.create(router)
}

class LocalNavigatorProvider @Inject constructor(
    private val cicerone: Cicerone<FlowRouter>
) : Provider<NavigatorHolder> {
    override fun get(): NavigatorHolder = cicerone.navigatorHolder
}