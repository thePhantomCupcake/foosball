package com.epiuse.labs.epifoos.match

import com.epiuse.labs.epifoos.game.Game
import com.epiuse.labs.epifoos.player.Player
import com.epiuse.labs.epifoos.player.PlayerEloService
import com.epiuse.labs.epifoos.player.Players
import org.jetbrains.exposed.sql.transactions.transaction

object MatchService {

    fun captureMatch(captureMatchRequest: CaptureMatchRequest): MatchSummary = transaction {
        val newMatch = Match.new {
            capturedBy = Player.findById(captureMatchRequest.capturedBy)!!
        }

        val playerIds = captureMatchRequest.getPlayerIds()
        val players = Player.find { Players.id inList playerIds }.associateBy { it.id.toString() }

        if (players.size < 4) {
            throw TooFewPlayersException("Cannot capture match with fewer than 4 valid players")
        }

        for (it in captureMatchRequest.gameResults) {
            Game.new {
                match = newMatch
                leftPlayer1 = players[it.leftPlayer1]!!
                leftPlayer2 = players[it.leftPlayer2]!!
                rightPlayer1 = players[it.rightPlayer1]!!
                rightPlayer2 = players[it.rightPlayer2]!!
                leftScore1 = it.leftScore1
                leftScore2 = it.leftScore2
                rightScore1 = it.rightScore1
                rightScore2 = it.rightScore2
            }
        }

        PlayerEloService.updateEloForPlayers(newMatch)

        newMatch.toSummary()
    }
}

class TooFewPlayersException(message: String) : Exception(message)
