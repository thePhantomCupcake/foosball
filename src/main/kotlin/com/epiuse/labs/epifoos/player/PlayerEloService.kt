package com.epiuse.labs.epifoos.player

import com.epiuse.labs.epifoos.game.Game
import com.epiuse.labs.epifoos.match.Match
import kotlin.math.pow

object PlayerEloService {

    private const val STARTING_ELO = 1000.0
    private const val STARTING_CHANGE = 0.0
    private const val ELO_WEIGHT = 400.0

    private const val RESULT_WEIGHT = 0.5
    private const val GOAL_WEIGHT = 1.0 - RESULT_WEIGHT

/*    fun updateEloForPlayers(match: Match) {
        val players = match.getPlayers()
        val currentElos = findCurrentElos(players)
        val eloChanges = calculateMatchEloChanges(match, currentElos)

        players.map {
            val previousElo = currentElos[it.id.toString()]!!
            val eloChange = eloChanges[it.id.toString()]!!
            val updatedElo = previousElo + eloChange

            PlayerElo.new {
                this.player = it
                this.capturedDate = match.capturedDate
                this.match = match
                this.change = eloChange
                this.elo = updatedElo
            }
        }

    }

    private fun findCurrentElos(players: List<Player>): Map<String, Float> {
        return PlayerElos.findLatestElos(players.map { it.id.value }).associate { it.player.id.value to it.elo }
    }

    private fun calculateMatchEloChanges(match: Match, startElos: Map<String, Float>): Map<String, Float> {
        return match.getPlayers().associate {
            it.id.value to match.games.fold(0.0) { initial, game ->
                {
                    initial+calculateGameEloChanges()
                }
            }.toFloat()
        }.toMap()
    }

    private fun calculateGameEloChanges(game: Game, startElos: Map<String, Float>): Map<String, Float> {
        val leftEloAverage = (startElos[game.leftPlayer1] + startElos[game.leftPlayer2]) / 2
        val rightEloAverage = (startElos[game.rightPlayer1] + startElos[game.rightPlayer2]) / 2

        val leftExpectedScore = estimateScoreVersus(leftEloAverage, rightEloAverage)
        val rightExpectedScore = 1.0 - leftExpectedScore

        val resultBasedEloChanges = calculateResultBasedEloChanges(game, leftExpectedScore, rightExpectedScore)
        val goalBasedEloChanges = calculateGoalBasedEloChanges(game, leftExpectedScore, rightExpectedScore)

        game.players.map { player ->
            player -> (resultBasedEloChanges(player) * ResultWeight+goalBasedEloChanges(player) * GoalWeight)
        }.toMap
    }

    private fun estimateScoreVersus(playerElo: Double, opponentElo: Double): Double {
        return 1.0 / (1.0 + 10.0.pow(((opponentElo - playerElo) / ELO_WEIGHT)))
    }*/
}

