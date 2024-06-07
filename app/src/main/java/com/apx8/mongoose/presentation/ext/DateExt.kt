package com.apx8.mongoose.presentation.ext

import org.joda.time.LocalDate

/**
 * 일자 리스트
 * @desc [오늘, 내일, 모레, 글피]
 */
fun getDateAfter2DaysWithToday(): List<String> {
    return mutableListOf<String>().also { list ->
        (0..3).forEach { day ->
            list.add(
                LocalDate
                    .now()
                    .plusDays(day)
                    .toString("yyyy-MM-dd")
            )
        }
    }
}

/**
 * 시간변환
 * @example `16:00` -> 16
 */
fun String.getDateTo24Hour(): Int {
    if (this.isBlank() || !this.contains(":")) return 0
    return this.split(":").first().toInt()
}

/**
 * 날짜변환
 * @example `2024-06-08` -> `06월08일`
 */
fun String.getDateToDay(): String {
    if (this.isBlank()) return "UNKNOWN"

    val dayDt = this.splitExt(" ").first()

    if (!dayDt.contains("-")) {
        return "UNKNOWN"
    }

    val daySpd = dayDt.splitExt("-")
    return "${daySpd[1]}월${daySpd[2]}일"
}