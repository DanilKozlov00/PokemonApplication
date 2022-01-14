package com.example.myapplication.Data

data class Pokemon(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)