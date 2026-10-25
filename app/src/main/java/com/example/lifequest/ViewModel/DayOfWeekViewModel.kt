package com.example.lifequest.ViewModel
import java.time.DayOfWeek

class DayOfWeekViewModel {
    companion object {
        fun encodeDays(days: Set<DayOfWeek>): String {
            return days.joinToString(",") { it.name }
        }

        fun decodeDays(value: String): Set<DayOfWeek> {
            if (value.isBlank()) return emptySet()

            return value.split(",")
                .map { DayOfWeek.valueOf(it) }
                .toSet()
        }
    }

}