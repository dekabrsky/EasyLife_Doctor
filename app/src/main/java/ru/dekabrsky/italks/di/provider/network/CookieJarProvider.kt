package ru.dekabrsky.easylife.di.provider.network

import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import java.net.CookieManager
import javax.inject.Inject
import javax.inject.Provider

class CookieJarProvider @Inject constructor(private val cookieManager: CookieManager) : Provider<CookieJar> {
    override fun get() = JavaNetCookieJar(cookieManager)
}
