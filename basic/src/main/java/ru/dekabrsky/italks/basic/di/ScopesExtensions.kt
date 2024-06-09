package ru.dekabrsky.easylife.basic.di

import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

fun module(func: (Module.() -> (Unit))) = object : Module() {
    init {
        func()
    }
}

fun Scope.inject(any: Any) = Toothpick.inject(any, this)

fun Scope.module(func: Module.() -> Unit): Scope {
    installModules(ru.dekabrsky.easylife.basic.di.module { func(this) })
    return this
}

fun Scope.install(module: Module): Scope {
    installModules(module)
    return this
}