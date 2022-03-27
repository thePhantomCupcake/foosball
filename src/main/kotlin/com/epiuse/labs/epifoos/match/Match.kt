package com.epiuse.labs.epifoos.match

import com.epiuse.labs.epifoos.game.Game
import com.epiuse.labs.epifoos.game.GameSummary
import com.epiuse.labs.epifoos.game.Games
import com.epiuse.labs.epifoos.player.Player
import com.epiuse.labs.epifoos.player.Players
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter


/**
 * A Match consists of multiple Games
 */
object Matches : IntIdTable(("match")) {
    val capturedDate: Column<Instant> = timestamp("captured_on").default(Instant.now())
    val capturedBy = reference("captured_by", Players)
}

class Match(id: EntityID<Int>) : IntEntity(id) {

    var capturedBy by Player referencedOn Matches.capturedBy
    var capturedDate by Matches.capturedDate
    val games by Game referrersOn Games.match

    fun getPlayers(): List<Player> {
        val firstGame = games.first()
        return listOf(
            firstGame.leftPlayer1,
            firstGame.leftPlayer2,
            firstGame.rightPlayer1,
            firstGame.rightPlayer2
        )
    }

    fun toSummary() = MatchSummary(
        matchId = id.value,
        games = games.map { it.toSummary() },
        capturedBy = capturedBy.id.value,
        capturedDate = DateTimeFormatter.ISO_INSTANT.format(capturedDate)
    )

    companion object : IntEntityClass<Match>(Matches) {

        val K_VALUE = 36.0;
    }
}

data class MatchSummary(
    var matchId: Int,
    var games: List<GameSummary>,
    var capturedBy: String,
    var capturedDate: String
)