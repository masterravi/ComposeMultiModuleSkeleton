package com.training.skeleton.feature_dashboard.data

data class Product(
    val limit: Int,
    val products: List<ProductDetail>,
    val skip: Int,
    val total: Int
)