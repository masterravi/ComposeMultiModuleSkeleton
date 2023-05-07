package com.training.network.response

data class Product(
    val limit: Int,
    val products: List<ProductDetail>,
    val skip: Int,
    val total: Int
)