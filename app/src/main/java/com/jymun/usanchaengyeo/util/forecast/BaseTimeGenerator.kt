package com.jymun.usanchaengyeo.util.forecast

import java.time.LocalDateTime

object BaseTimeGenerator {

    private val baseDateTime: LocalDateTime
        get() = with(LocalDateTime.now()) {
            if (this.minute < 30) this.minusMinutes(this.minute + 1L)
            else this
        }

    val date: String
        get() = baseDateTime.toLocalDate().toString().filter { it != '-' }

    val time: String
        get() = baseDateTime.toLocalTime().toString().substring(0, 5).filter { it != ':' }
}