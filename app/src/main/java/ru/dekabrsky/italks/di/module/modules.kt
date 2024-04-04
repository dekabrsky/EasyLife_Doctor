package ru.dekabrsky.italks.di.module

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.core.app.NotificationManagerCompat
import androidx.room.RoomDatabase
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import io.reactivex.Scheduler
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.notifications.implementation.NotificationChannelManager
import ru.dekabrsky.feature.notifications.implementation.data.provider.NotificationDatabaseProvider
import ru.dekabrsky.italks.basic.di.NotificationDatabaseQualifier
import ru.dekabrsky.italks.basic.di.ServerEndpoint
import ru.dekabrsky.italks.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.urlProvider.UrlProvider
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.di.provider.network.*
import ru.dekabrsky.italks.navigation.AppFlowFragmentProvider
import ru.dekabrsky.feature.notifications.implementation.provider.NotificationManagerCompatProvider
import ru.dekabrsky.italks.basic.di.AppActivityProvider
import ru.dekabrsky.italks.activity.provider.AppActivityProviderImpl
import ru.dekabrsky.italks.basic.di.WSOkHttpClient
import ru.dekabrsky.ws.implementation2.WsService
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import java.net.CookieManager

class AppRootModule(app: Application) : Module() {
    init {
        bind(Context::class.java).toInstance(app.applicationContext)
        bind(ResourceProvider::class.java).singletonInScope()

        bind(String::class.java)
            .withName(ServerEndpoint::class.java)
            .toProvider(EndPointProvider::class.java)

        bind(AppActivityProvider::class.java).to(AppActivityProviderImpl::class.java)
    }
}

class AppModule : Module() {
    init {
        val cicerone = Cicerone.create(FlowRouter())

        bind(Router::class.java).toInstance(cicerone.router)
        bind(AppRouter::class.java).toInstance(cicerone.router)
        bind(FlowRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(RoomDatabase::class.java).withName(NotificationDatabaseQualifier::class.java)
            .toProvider(NotificationDatabaseProvider::class.java).providesSingletonInScope()
        bind(MediaPlayer::class.java).toProvider(MediaPlayerProvider::class.java).providesSingletonInScope()
        bind(FirebaseAnalytics::class.java).toProvider(FirebaseAnalyticsProvider::class.java).providesSingletonInScope()
        bind(MixpanelAPI::class.java).toProvider(MixPanelApiProvider::class.java).providesSingletonInScope()
        bind(AnalyticsSender::class.java).singletonInScope()
        bind(LoginDataCache::class.java).singletonInScope()
    }
}

class FlowModule : Module() {
    init {
        bind(FlowFragmentProvider::class.java).to(AppFlowFragmentProvider::class.java)
    }
}

class NetworkModule : Module() {
    init {
        bind(UrlProvider::class.java).to(AppUrlProvider::class.java)
        bind(Scheduler::class.java).toInstance(RxSchedulers.io())

        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java)
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
        bind(OkHttpClient::class.java).withName(WSOkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)

        bind(CallAdapter.Factory::class.java).toProvider(CallAdapterFactoryProvider::class.java)
            .providesSingletonInScope()
        bind(BaseModelErrorHandlerFactory::class.java).to(ModelErrorHandlerFactoryImpl::class.java)
            .singletonInScope()
        bind(CookieManager::class.java).toInstance(CookieManager())
        bind(CookieJar::class.java).toProvider(CookieJarProvider::class.java).providesSingletonInScope()

        bind(WsService::class.java).singletonInScope()
    }
}

class PushModule : Module() {
    init {
        bind(NotificationChannelManager::class.java).singletonInScope()
        bind(NotificationManagerCompat::class.java)
            .toProvider(NotificationManagerCompatProvider::class.java)
    }
}
