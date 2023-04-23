package com.training.skeleton.feature_dashboard.data

data class Anime(
    val description: String,
    val genres: List<String>,
    val hype: Int,
    val start_date: String,
    val studio: String,
    val title: Title
)

data class Title(
    val link: String,
    val text: String
)