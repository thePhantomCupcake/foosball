package com.epiuse.labs.epifoos.match

import com.epiuse.labs.epifoos.player.Players
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object MatchResults : IntIdTable("player_match_result") {

    var player = reference("player", Players)
    var result = enumerationByName("result", 255, MatchResult.Result::class)
    var rank = integer("rank")
    var gameScore = integer("game_score")
    var goalScore = integer("goal_score")
}

class MatchResult(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<MatchResult>(MatchResults)

    enum class Result(val value: String) {
        WINNER("Winner"),
        LOSER("Loser"),
        NO_RESULT("NoResult")
    }
}