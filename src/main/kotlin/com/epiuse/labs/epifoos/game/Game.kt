package com.epiuse.labs.epifoos.game

import com.epiuse.labs.epifoos.match.Match
import com.epiuse.labs.epifoos.match.Matches
import com.epiuse.labs.epifoos.player.Player
import com.epiuse.labs.epifoos.player.Players
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Games : IntIdTable("game") {
    var match = reference("match_id", Matches)
    var leftPlayer1 = reference("left_player_1", Players)
    var leftPlayer2 = reference("left_player_2", Players)
    var rightPlayer1 = reference("right_player_1", Players)
    var rightPlayer2 = reference("right_player_2", Players)
    var leftScore1 = integer("left_score_1")
    var leftScore2 = integer("left_score_2")
    var rightScore1 = integer("right_score_1")
    var rightScore2 = integer("right_score_2")
}

class Game(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Game>(Games)

    var match by Match referencedOn Games.match
    var leftPlayer1 by Player referencedOn Games.leftPlayer1
    var leftPlayer2 by Player referencedOn Games.leftPlayer2
    var rightPlayer1 by Player referencedOn Games.rightPlayer1
    var rightPlayer2 by Player referencedOn Games.rightPlayer2
    var leftScore1 by Games.leftScore1
    var leftScore2 by Games.leftScore2
    var rightScore1 by Games.rightScore1
    var rightScore2 by Games.rightScore2

    fun toSummary() = GameSummary(
        matchId = match.id.value,
        leftPlayer1 = leftPlayer1.id.toString(),
        leftPlayer2 = leftPlayer2.id.toString(),
        rightPlayer1 = rightPlayer1.id.toString(),
        rightPlayer2 = rightPlayer2.id.toString(),
        leftScore1 = leftScore1,
        leftScore2 = leftScore2,
        rightScore1 = rightScore1,
        rightScore2 = rightScore2
    )
}

data class GameSummary(
    var matchId: Int,
    var leftPlayer1: String,
    var leftPlayer2: String,
    var rightPlayer1: String,
    var rightPlayer2: String,
    var leftScore1: Int,
    var leftScore2: Int,
    var rightScore1: Int,
    var rightScore2: Int
)