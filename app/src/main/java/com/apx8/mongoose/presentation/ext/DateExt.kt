package com.apx8.mongoose.presentation.ext

import org.joda.time.LocalDate


/**
 * Date External Functions
 */

const val DOC_DATE_FORMAT_HYPHEN = "yyyy-MM-dd"

fun getDateAfter2DaysWithToday(): List<String> {
    return mutableListOf<String>().also { list ->
        (0..3).forEach {
            list.add(getDateAfterTomorrow(it))
        }
    }
}

internal fun getDateAfterTomorrow(afterDay: Int): String {
    return LocalDate.now().plusDays(afterDay).toString(DOC_DATE_FORMAT_HYPHEN)
}

fun String.getDateTo24Hour(): Int {
    if (this.isBlank() || !this.contains(":")) return 0

    val spd = this.split(":")
    return spd.first().toInt()
}

fun String.getDateToDay(): String {
    if (this.isBlank()) return "UNKNOWN"

    val spd = this.splitExt(" ")
    val dayDt = spd.first()

    if (!dayDt.contains("-")) {
        return "UNKNOWN"
    }

    val daySpd = dayDt.splitExt("-")
    return "${daySpd[1]}월${daySpd[2]}일"
}