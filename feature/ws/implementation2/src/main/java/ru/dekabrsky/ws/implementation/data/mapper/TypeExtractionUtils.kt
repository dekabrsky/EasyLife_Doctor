package ru.dekabrsky.ws.implementation2.data.mapper

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object TypeExtractionUtils {

    fun getType(rawClass: Class<*>, parameter: Class<*>): Type {
        return object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(parameter)
            }

            override fun getRawType(): Type {
                return rawClass
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
    }

}