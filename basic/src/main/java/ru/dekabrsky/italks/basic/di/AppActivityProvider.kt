package ru.dekabrsky.italks.basic.di

interface AppActivityProvider {
    fun get(): Class<*>
}