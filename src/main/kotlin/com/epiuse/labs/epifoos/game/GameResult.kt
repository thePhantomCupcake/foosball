package com.epiuse.labs.epifoos.game

data class GameResult(
    val leftPlayer1: String,
    val leftPlayer2: String,
    val rightPlayer1: String,
    val rightPlayer2: String,
    val leftScore1: Int,
    val leftScore2: Int,
    val rightScore1: Int,
    val rightScore2: Int
)