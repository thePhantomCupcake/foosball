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

    fun updateEloForPlayers(match: Match): List<PlayerEloSummary> {
        val players = match.getPlayers()
        val currentElos = findCurrentElos(players)
        val eloChanges = calculateMatchEloChanges(match, currentElos)

        return players.map {
            val previousElo = currentElos[it.id.toString()]!!
            val eloChange = eloChanges[it.id.toString()]!!
            val updatedElo = previousElo + eloChange

            PlayerElo.new {
                this.player = it
                this.capturedDate = match.capturedDate
                this.match = match
                this.change = eloChange
                this.elo = updatedElo
            }.toSummary()
        }
    }

    private fun findCurrentElos(players: List<Player>): Map<String, Double> {
        return PlayerElos.findLatestElos(players.map { it.id.value }).associate { it.playerId to it.elo }
    }

    private fun calculateMatchEloChanges(match: Match, startElos: Map<String, Double>): Map<String, Double> {
        return match.getPlayers().associate {
            var playerElo = 0.0
            for (game in match.games) {
                playerElo += calculateGameEloChanges(game, startElos)[it.id.value]!!
            }
            it.id.value to playerElo
        }
    }

    private fun calculateGameEloChanges(game: Game, startElos: Map<String, Double>): Map<String, Double> {
        val leftEloAverage = (startElos[game.leftPlayer1.id.value]!! +
                startElos[game.leftPlayer2.id.value]!!) / 2
        val rightEloAverage = (startElos[game.rightPlayer1.id.value]!! +
                startElos[game.rightPlayer2.id.value]!!) / 2

        val leftExpectedScore = estimateScoreVersus(leftEloAverage, rightEloAverage)
        val rightExpectedScore = 1.0 - leftExpectedScore

        val resultBasedEloChanges = calculateResultBasedEloChanges(game, leftExpectedScore, rightExpectedScore)
        val goalBasedEloChanges = calculateGoalBasedEloChanges(game, leftExpectedScore, rightExpectedScore)

        return game.match.getPlayers().associate {
            it.id.value to (
                    resultBasedEloChanges[it.id.value]!! *
                            RESULT_WEIGHT +
                            goalBasedEloChanges[it.id.value]!! *
                            GOAL_WEIGHT
                    )
        }.toMap()
    }

    private fun calculateResultBasedEloChanges(
        game: Game, leftExpectedScore: Double,
        rightExpectedScore: Double
    ): Map<String, Double> {
        val leftActualScore = game.leftResult().value / Game.Result.MAX
        val rightActualScore = game.rightResult().value / Game.Result.MAX

        return game.match.getPlayers().associate {
            val eloChange = if (game.leftPlayers().contains(it)) Match.K_VALUE * (leftActualScore - leftExpectedScore)
            else Match.K_VALUE * (rightActualScore - rightExpectedScore)
            it.id.value to eloChange
        }
    }

    private fun calculateGoalBasedEloChanges(
        game: Game, leftExpectedScore: Double,
        rightExpectedScore: Double
    ): Map<String, Double> {
        val normalizeScore: (Int) -> Double = {
            (it + Game.MAX_SCORE) / (2.0 * Game.MAX_SCORE)
        }

        val leftGoalDifference = (game.leftTotal() - game.rightTotal())
        val rightGoalDifference = (game.rightTotal() - game.leftTotal())

        val leftActualScore = normalizeScore(leftGoalDifference)
        val rightActualScore = normalizeScore(rightGoalDifference)

        return game.match.getPlayers().associate {
            val eloChange = if (game.leftPlayers().contains(it)) Match.K_VALUE * (leftActualScore - leftExpectedScore)
            else Match.K_VALUE * (rightActualScore - rightExpectedScore)
            it.id.value to eloChange
        }.toMap()
    }

    private fun estimateScoreVersus(playerElo: Double, opponentElo: Double): Double {
        return 1.0 / (1.0 + 10.0.pow(((opponentElo - playerElo) / ELO_WEIGHT)))
    }
}

