package com.epiuse.labs.epifoos.player

import com.epiuse.labs.epifoos.match.Match
import com.epiuse.labs.epifoos.match.Matches
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction

object PlayerElos : IntIdTable("player_elo") {

    var player = reference("player", Players)
    var capturedDate = timestamp("captured_date")
    var match = reference("match_id", Matches)
    var change = float("change")
    var elo = float("elo")

    fun findLatestElos(playerIds: List<String>): List<PlayerElo> {
        val latestElos = mutableListOf<PlayerElo>()
        transaction {
            PlayerElos.run {
                select {
                    id inSubQuery slice(id.max()).selectAll().groupBy(player).having { player inList playerIds }
                }
            }
        }
        return latestElos
    }
}

class PlayerElo(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<PlayerElo>(PlayerElos)

    var player by Player referencedOn PlayerElos.player
    var capturedDate by PlayerElos.capturedDate
    var match by Match referencedOn PlayerElos.match
    var change by PlayerElos.change
    var elo by PlayerElos.elo
}