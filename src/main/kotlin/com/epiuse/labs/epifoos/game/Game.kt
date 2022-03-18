package com.epiuse.labs.epifoos.game

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

    var leftPlayer1 by Player referencedOn Players.id
    var leftPlayer2 by Player referencedOn Players.id
    var rightPlayer1 by Player referencedOn Players.id
    var rightPlayer2 by Player referencedOn Players.id
    var leftScore1 by Games.leftScore1
    var leftScore2 by Games.leftScore2
    var rightScore1 by Games.rightScore1
    var rightScore2 by Games.rightScore2
}