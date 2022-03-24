package com.epiuse.labs.epifoos.match

import com.epiuse.labs.epifoos.game.GameResult

data class CaptureMatchRequest(

    val capturedBy: String,
    val gameResults: List<GameResult>
) {

    fun getPlayerIds(): List<String> {
        val firstGame = gameResults.first()
        return listOf(
            firstGame.leftPlayer1,
            firstGame.leftPlayer2,
            firstGame.rightPlayer1,
            firstGame.rightPlayer2
        )
    }
}
