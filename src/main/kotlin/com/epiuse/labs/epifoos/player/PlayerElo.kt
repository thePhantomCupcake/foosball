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
import java.time.format.DateTimeFormatter.ISO_INSTANT

object PlayerElos : IntIdTable("player_elo") {

    var player = reference("player", Players)
    var capturedDate = timestamp("captured_date")
    var match = reference("match_id", Matches)
    var change = double("change")
    var elo = double("elo").default(PlayerElo.INITIAL_ELO)

    fun findLatestElo(playerId: String): PlayerEloSummary? {
        return transaction {
            PlayerElo.find { player eq playerId }.maxByOrNull { capturedDate }
        }?.toSummary()
    }

    fun findLatestElos(playerIds: List<String>): List<PlayerElo> {
        return transaction {
            PlayerElos.run {
                select {
                    id inSubQuery slice(id.max()).selectAll().groupBy(player).having { player inList playerIds }
                }
            }
        }.map { PlayerElo.wrapRow(it) }
    }
}

class PlayerElo(id: EntityID<Int>) : IntEntity(id) {

    var player by Player referencedOn PlayerElos.player
    var capturedDate by PlayerElos.capturedDate
    var match by Match referencedOn PlayerElos.match
    var change by PlayerElos.change
    var elo by PlayerElos.elo

    fun toSummary(): PlayerEloSummary {
        return PlayerEloSummary(
            player.id.value,
            ISO_INSTANT.format(capturedDate),
            match.id.value,
            change,
            elo
        )
    }

    companion object : IntEntityClass<PlayerElo>(PlayerElos) {

        const val INITIAL_ELO: Double = 1000.0
    }
}

data class PlayerEloSummary(
    val playerId: String,
    val capturedDate: String,
    val matchId: Int,
    val change: Double,
    val elo: Double
)