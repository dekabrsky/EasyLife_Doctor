package ru.dekabrsky.italks.basic.network.utils

import com.google.gson.Gson
import retrofit2.HttpException
import ru.dekabrsky.italks.basic.R
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.model.ErrorResponseBody
import ru.dekabrsky.italks.basic.network.model.ReLoginType
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import java.net.UnknownHostException
import javax.inject.Inject

class ServerErrorHandler @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val router: FlowRouter
) {
    fun onError(e: Throwable, view: BasicView) {
        val errorBody = tryParseErrorBody(e)

        when {
            errorBody != null
                    && errorBody.statusCode == UNAUTHORIZED_ERROR_CODE
                    && errorBody.error.code == EXPIRED_AUTH_DATA
            -> {
                router.newRootFlow("FLOW_LOGIN", ReLoginType.PASSWORD)
                view.showToast(errorBody.error.message)
            }

//            errorBody != null
//                    && errorBody.statusCode == UNAUTHORIZED_ERROR_CODE
//                    && errorBody.error.code == INVALID_AUTH_DATA
//            ->
//                view.showError(errorBody.error.message) // todo на логине обработать

            (e as? HttpException)?.response()?.code() == UNAUTHORIZED_ERROR_CODE
                    || errorBody?.statusCode == UNAUTHORIZED_ERROR_CODE -> {
                router.newRootFlow("FLOW_LOGIN", ReLoginType.PIN)
                errorBody?.error?.let { view.showToast(it.message) }
            }

            errorBody != null ->
                view.showError(errorBody.error.message)

            e is UnknownHostException ->
                view.showError(resourceProvider.getString(R.string.unknown_host_error))

            else ->
                view.showError(resourceProvider.getString(R.string.unknown_error))
        }

    }

    private fun tryParseErrorBody(e: Throwable): ErrorResponseBody? {
        return try {
            val errorBody = (e as? HttpException)?.response()?.errorBody()
            Gson().fromJson(errorBody?.string(), ErrorResponseBody::class.java)
        } catch (e: Throwable) {
            null
        }
    }

    companion object {
        private const val UNAUTHORIZED_ERROR_CODE = 401
        private const val INVALID_AUTH_DATA = "invalid_auth_data"
        private const val EXPIRED_AUTH_DATA = "expired_auth_data"
    }
}