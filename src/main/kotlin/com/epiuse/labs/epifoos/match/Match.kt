package com.epiuse.labs.epifoos.match

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column


/**
 * A Match consists of multiple Games
 */
object Matches : IntIdTable(("match")) {
    val matchId: Column<Long> = long("match_id")
    val leftPlayer1: Column<String> = varchar("left_player_1", 255)
    val leftPlayer2: Column<String> = varchar("left_player_2", 255)
    val rightPlayer1: Column<String> = varchar("right_player_1", 255)
    val rightPlayer2: Column<String> = varchar("right_player_2", 255)

    enum class Format(val kValue: Double) {

        CompleteMatch(36.0),
        SingleGame(12.0)
    }
}

class Match(id: EntityID<Int>): IntEntity(id)  {
    companion object : IntEntityClass<Match>(Matches)

    var matchId by Matches.matchId
    var leftPlayer1 by Matches.leftPlayer1
    var leftPlayer2 by Matches.leftPlayer2
    var rightPlayer1 by Matches.rightPlayer1
    var rightPlayer2 by Matches.rightPlayer2
}