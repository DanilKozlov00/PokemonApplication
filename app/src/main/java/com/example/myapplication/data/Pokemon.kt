package com.example.myapplication.data

data class Pokemon(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)