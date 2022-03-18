package com.epiuse.labs.epifoos.player

import com.epiuse.labs.epifoos.match.Match
import com.epiuse.labs.epifoos.match.Matches
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object PlayerElos : IntIdTable("player_elo") {

    var player = reference("player", Players)
    var capturedDate = timestamp("captured_date")
    var match = reference("match_id", Matches)
    var change = double("change")
    var elo = double("elo")
}

class PlayerElo(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<PlayerElo>(PlayerElos)

    var player by Player referencedOn Players.id
    var capturedDate by PlayerElos.capturedDate
    var match by Match referencedOn Matches.id
    var change by PlayerElos.change
    var elo by PlayerElos.elo
}