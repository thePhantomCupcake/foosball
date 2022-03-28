package com.epiuse.labs.epifoos.player

data class PlayerSummary(
    val emailAddress: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val latestElo: PlayerEloSummary?
)