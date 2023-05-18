package ru.dekabrsky.dialings.domain.interactor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.dekabrsky.dialings.domain.model.PlainProduct
import javax.inject.Inject


class ProductsInteractor @Inject constructor() {

    private val typeToken = object : TypeToken<List<PlainProduct>>() {}.type
    private var products = listOf<PlainProduct>()

    fun parseProducts(json: String?){
        products = Gson().fromJson<List<PlainProduct>?>(json, typeToken).filter { it.avg != -1.0 }
    }

    fun getProducts(
        cities: List<String> = listOf(),
        types: List<String> = listOf()
    ): List<PlainProduct> {
        return products
            .filter { cities.isEmpty() || cities.contains(it.city) }
            .filter { types.isEmpty() || types.contains(it.name) }
    }

    fun getCities() = products.distinctBy { it.city }.map { it.city }

    fun getTypes() = products.distinctBy { it.name }.map { it.name }
}