package com.epiuse.labs.epifoos.player

import java.time.LocalDateTime

data class Match(val id: Long, val capturedDate: LocalDateTime, val capturedBy: String, val format: Format) {

    sealed class Format(val name: String, val kValue: Double) {

        class SingleMatch(): Format("SingleMatch", 12.0)

        class CompleteGame(): Format("CompleteGame", 36.0)

        override fun toString(): String = name
    }
}


