package com.epiuse.labs.epifoos.match

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


/**
 * A Match consists of multiple Games
 */
object Match : Table("match") {
    val id: Column<Long> = long("id").autoIncrement()
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