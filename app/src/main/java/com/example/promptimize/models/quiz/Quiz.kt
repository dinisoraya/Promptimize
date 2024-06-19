package com.example.promptimize.models.quiz

data class Quiz(
    val choices: List<String>,
    val correctAnswer: String,
    val id: Int,
    val question: String
)