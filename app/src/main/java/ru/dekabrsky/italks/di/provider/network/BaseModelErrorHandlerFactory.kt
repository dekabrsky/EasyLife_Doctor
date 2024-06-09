package ru.dekabrsky.easylife.di.provider.network

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/**
 * Интерфейс, для преобразования моделей в ошибки.
 * Это полезно, когда с сервера приходит HTTP статус 200, но по факту это ошибка
 */
interface ModelErrorHandler<in M> {

    /**
     * Анализирует входящую модель на наличие ошибки, если ошибка есть, то вернет ошибку,
     * если нет, то будет возвращен null
     */
    fun convert(model: M): Throwable?
}

abstract class BaseModelErrorHandlerFactory {

    abstract val map: ConcurrentHashMap<Class<*>, ModelErrorHandler<*>>

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getConverter(response: T): ModelErrorHandler<T> {
        val converter = map[response.javaClass] as ModelErrorHandler<T>?
        return converter ?: DefaultConverter()
    }
}

private class DefaultConverter<in T> : ModelErrorHandler<T> {
    override fun convert(model: T): Throwable? = null
}

class ModelErrorHandlerFactoryImpl @Inject constructor() : BaseModelErrorHandlerFactory() {
    override val map = ConcurrentHashMap<Class<*>, ModelErrorHandler<*>>()
}