package ru.dekabrsky.italks.basic.navigation.di

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

fun localNavigationModules(func: (Module.() -> Unit)?) = object : Module() {
    init {
        bind(FlowRouter::class.java).toProvider(LocalRouterProvider::class.java).providesSingletonInScope()
        bind(Cicerone::class.java).toProvider(LocalCiceroneProvider::class.java).singletonInScope()
        bind(NavigatorHolder::class.java).toProvider(LocalNavigatorProvider::class.java).providesSingletonInScope()
        func?.let { func(this) }
    }
}

fun Scope.installNavigation(func: (Module.() -> Unit)? = null): Scope {
    installModules(localNavigationModules { func?.invoke(this) })
    return this
}