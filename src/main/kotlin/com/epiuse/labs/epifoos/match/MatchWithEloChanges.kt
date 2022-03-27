package com.epiuse.labs.epifoos.match

import com.epiuse.labs.epifoos.player.PlayerEloSummary

data class MatchWithEloChanges(
    val match: MatchSummary,
    val playerEloChanges: List<PlayerEloSummary>
)