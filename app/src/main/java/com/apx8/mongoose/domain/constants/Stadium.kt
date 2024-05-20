package com.apx8.mongoose.domain.constants

import androidx.annotation.DrawableRes
import com.apx8.mongoose.R

/**
 * 범주
 * SOJ (서울종합운동장 야구장)
 * SKW (수원 케이티 위즈 파크)
 * ILF (인천 SSG 랜더스필드)
 * CNP (창원 NC 파크)
 * GCF (광주-기아 챔피언스 필드)
 * BSJ (사직야구장)
 * BDP (대전 한화생명 이글스파크/베이스볼 드림파크)
     * -> 25년부터 `베이스볼 드림파크`로 사용하여, 코드명 수정
 * DSP (대구 삼성 라이온즈 파크)
 * SOG (고척 스카이돔)
 * UMS (울산 문수 야구장)
 * POH (포항 야구장)
 * CJB (청주종합운동장 야구장)
 */
sealed class Stadium(
    val name: String,
    val code: String,
    @DrawableRes val iconRes: Int,
    val lat: Double,
    val lon: Double
) {
    data object SOJ : Stadium(
        name = "서울종합운동장 야구장",
        code = "SOJ",
        iconRes = R.drawable.ic_sunny,
        lat = 37.512,
        lon = 127.071
    )
    data object SKW : Stadium(
        name = "수원 케이티 위즈 파크",
        code = "SKW",
        iconRes = R.drawable.ic_sunny,
        lat = 37.299,
        lon = 127.010
    )
    data object ILF : Stadium(
        name = "인천 SSG 랜더스필드",
        code = "ILF",
        iconRes = R.drawable.ic_sunny,
        lat = 37.436,
        lon = 126.693
    )
    data object CNP : Stadium(
        name = "창원 NC 파크",
        code = "CNP",
        iconRes = R.drawable.ic_sunny,
        lat = 35.222,
        lon = 128.582
    )
    data object GCF : Stadium(
        name = "광주-기아 챔피언스 필드",
        code = "GCF",
        iconRes = R.drawable.ic_sunny,
        lat = 35.168,
        lon = 126.889
    )
    data object BSJ : Stadium(
        name = "사직야구장",
        code = "BSJ",
        iconRes = R.drawable.ic_sunny,
        lat = 35.193,
        lon = 129.061
    )
    data object BDP : Stadium(
        name = "대전 한화생명 이글스 파크",
        code = "DEP",
        iconRes = R.drawable.ic_sunny,
        lat = 36.316,
        lon = 127.429
    )
    data object DSP : Stadium(
        name = "대구 삼성 라이온즈 파크",
        code = "DSP",
        iconRes = R.drawable.ic_sunny,
        lat = 35.841,
        lon = 128.681
    )
    data object SOG : Stadium(
        name = "고척 스카이돔",
        code = "SOG",
        iconRes = R.drawable.ic_sunny,
        lat = 37.497,
        lon = 126.867
    )
    data object UMS : Stadium(
        name = "울산 문수 야구장",
        code = "UMS",
        iconRes = R.drawable.ic_sunny,
        lat = 35.531,
        lon = 129.265
    )
    data object POH : Stadium(
        name = "포항 야구장",
        code = "POH",
        iconRes = R.drawable.ic_sunny,
        lat = 36.007,
        lon = 129.359
    )
    data object CJB : Stadium(
        name = "청주종합운동장 야구장",
        code = "CJB",
        iconRes = R.drawable.ic_sunny,
        lat = 36.638,
        lon = 127.470
    )
    data object UNKNOWN : Stadium(
        name = "알수없음",
        code = "UNKNOWN",
        iconRes = R.drawable.ic_sunny,
        lat = 37.549,
        lon = 126.991
    )


    companion object {
        fun fromCode(code: String): Stadium {
            return when(code) {
                "SOJ" -> SOJ
                "SKW" -> SKW
                "ILF" -> ILF
                "CNP" -> CNP
                "GCF" -> GCF
                "BSJ" -> BSJ
                "BDP" -> BDP
                "DSP" -> DSP
                "SOG" -> SOG
                "UMS" -> UMS
                "POH" -> POH
                "CJB" -> CJB
                else -> UNKNOWN
            }
        }
    }
}