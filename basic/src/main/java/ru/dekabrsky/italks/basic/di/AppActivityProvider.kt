package ru.dekabrsky.easylife.basic.di

interface AppActivityProvider {
    fun get(): Class<*>
}