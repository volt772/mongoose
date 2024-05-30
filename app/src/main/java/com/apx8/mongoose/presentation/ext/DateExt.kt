package com.apx8.mongoose.presentation.ext

import com.apx8.mongoose.R
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs


/**
 * Date External Functions
 */

const val DOC_DATE_FORMAT_HYPHEN = "yyyy-MM-dd"
const val DOC_DATE_FORMAT_DOT = "yy.MM.dd"
const val DOC_TIME_FORMAT = "HH:mm"
const val DOC_TIME_FORMAT_FULL = "HH:mm:ss"
const val DOC_FULL_FORMAT = "yy.MM.dd HH:mm"
const val DOC_RESP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"


val currMillis: Long
    get() {
        return System.currentTimeMillis()
    }

fun getDateAfter2DaysWithToday(): List<String> {
    return mutableListOf<String>().also { list ->
        (0..3).forEach {
            list.add(getDateAfterTomorrow(it))
        }
    }
}

fun getDateAfter2DaysWithTomorrow(): List<String> {
    return mutableListOf<String>().also { list ->
        list.add(getDateAfterTomorrow(1))
        list.add(getDateAfterTomorrow(2))
        list.add(getDateAfterTomorrow(3))
    }
}

internal fun getDateAfterTomorrow(afterDay: Int): String {
    return LocalDate.now().plusDays(afterDay).toString(DOC_DATE_FORMAT_HYPHEN)
}

///**
// * 모레, 글피 일자 가져오기
// * @return Pair<tomorrow, 2 days after tomorrow>
// */
//fun getDateAfter2Days(): Pair<String, String> {
//    return (getDateAfterTomorrow(1) to getDateAfterTomorrow(2))
//}

/**
 * Joda DateTime 이 올바른 값인지 검사.
 */
fun DateTime?.isValid() = this?.let { it.millis != Long.MIN_VALUE } ?: false

/**
 * 시간 Formatted
 * @param type
 */
fun DateTime?.convertDateByType(type: Int): String {
    this ?: return ""

    return when (type) {
        1 -> this.toString("YY.MM.dd HH:mm")
        2 -> this.toString("HH:mm")
        3 -> this.toString("YY-MM-dd HH:mm")
        4 -> this.toString("YYYY-MM-dd")
        5 -> this.toString("YY-MM-dd")
        6 -> this.toString("MM-dd (EEE)")
        7 -> this.toString("YYYY-MM")
        8 -> this.toString("YYYY-MM-dd (EEE)")
        9 -> this.toString("YYYY-MM-dd HH:mm")
        10 -> this.toString("YYYY-MM-dd")
        11 -> this.toString("MM-dd")
        else -> this.toString("YY.MM.dd HH:mm")
    }
}

/**
 * Date Convert
 * @desc String to Millis
 */
fun String?.formedDateToMillis(format : String = DOC_DATE_FORMAT_DOT): Long {
    val millis = this?.let { formed ->
        val sdf = SimpleDateFormat(format)
        val date: Date = sdf.parse(formed) as Date
        date.time
    } ?: 0L

    return millis
}

/**
 * Date Convert
 * @desc Millis to String
 */
fun Long?.millisToFormedDate(format : String = DOC_DATE_FORMAT_DOT): String {
    val formed = this?.let { millis ->
        val formatter = SimpleDateFormat(format)
        formatter.format(Date(millis))
    } ?: ""

    return formed
}

fun getMillis(d: Long): Long {
    val offset: Int = TimeZone.getDefault().getOffset(d)
    return (d + offset) / 86400000L * 86400000L - offset
}

/**
 * Today Millis
 */
fun getTodayMillis(): Long {
    val d = Date().time
    return getMillis(d)
}

fun getWeekMillis(day: Int): Long {
    val d = Date()
    val cal = Calendar.getInstance()
    cal.setTime(d)
    cal.add(Calendar.DAY_OF_YEAR, day)
    return getMillis(cal.time.time)
}

/**
 * Diff Days From Today
 */
fun Long.getDfFromToday(): Int {
    /* 오늘*/
    val today = getTodayMillis()
    val todayFormed = today.millisToFormedDate(DOC_DATE_FORMAT_HYPHEN)

    /* 대상*/
    val target = this
    val targetFormed = target.millisToFormedDate(DOC_DATE_FORMAT_HYPHEN)

    /* 오늘, 대상 -> Parsing*/
    val dateA = LocalDate.parse(todayFormed)
    val dateB = LocalDate.parse(targetFormed)

    /* DIFF*/
    val daysDiff = abs(Days.daysBetween(dateA, dateB).days).toLong()

    /* Before OR After*/
    return if (daysDiff > 0 && target < today) {
        daysDiff * -1
    } else {
        daysDiff
    }.toInt()
}

/**
 * D-Day String Maker
 * @desc "yy.MM.dd (D-NN)"
 */
fun Long.convertDateLabel(onlyDay: Boolean = false): String {
    val dateLabel = this.millisToFormedDate()
    val dfDays = this.getDfFromToday()
    val dDayLabel = if (dfDays != 0) {
        if (dfDays < 0) {
            getStringRes(R.string.day_past).format(dfDays * -1)
        } else {
            getStringRes(R.string.day_future).format(dfDays)
        }
    } else {
        getStringRes(R.string.day_on_day)
    }

    val label = if (onlyDay) {
        "%s".format(dDayLabel)
    } else {
        "%s (%s)".format(dateLabel, dDayLabel)
    }

    return label
}

fun getTimeUsingInWorkRequest() : Long {
    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance()

    dueDate.set(Calendar.HOUR_OF_DAY, 17)
    dueDate.set(Calendar.MINUTE, 0)
    dueDate.set(Calendar.SECOND, 0)

    if(dueDate.before(currentDate)) {
        dueDate.add(Calendar.HOUR_OF_DAY, 24)
    }

    return dueDate.timeInMillis - currentDate.timeInMillis
}

fun String.getDateTo24Hour(): Int {
    if (this.isBlank()) return 0

    val spd = this.splitExt(" ")
    val hourDt = spd.last()

    if (!hourDt.contains(":")) {
        return 0
    }

    val hourSpd = hourDt.splitExt(":")
    return hourSpd.first().toInt()
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


fun String.getDateToAbbr(divider: String): String {
    if (this.isBlank()) return ""

    val year = this.substring(2, 4)
    val month = this.substring(4, 6)
    val day = this.substring(6, 8)

    return String.format(Locale.getDefault(), "%s%s%s%s%s", year, divider, month, divider, day)
}

/**
 * Convert Date Format
 * @desc millis -> yy.MM.dd
 */
fun Long?.convertDate(format : String = DOC_DATE_FORMAT_DOT): String {
    val formed = this?.let { millis ->
        val formatter = SimpleDateFormat(format)
        formatter.format(Date(millis))
    } ?: ""

    return formed
}