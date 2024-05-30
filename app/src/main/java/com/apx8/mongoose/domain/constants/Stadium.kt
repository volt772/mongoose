package com.apx8.mongoose.constants

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
 * BDP (대전 한화생명 이글스파크/베이스볼 드림파크) -> 25년부터 `베이스볼 드림파크`로 사용하여, 코드명 수정
 * DSP (대구 삼성 라이온즈 파크)
 * SOG (고척 스카이돔)
 * UMS (울산 문수 야구장)
 * POH (포항 야구장)
 * CJB (청주종합운동장 야구장)
 */
enum class Stadium(
    val signBoard: String,
    val code: String,
    @DrawableRes val signature: Int,
    val lat: Double,
    val lon: Double
) {
    SOJ("서울종합운동장 야구장", "SOJ",R.drawable.ic_sunny, 37.512, 127.071),
    SKW("수원 케이티 위즈 파크", "SKW", R.drawable.ic_sunny, 37.299, 127.010),
    ILF("인천 SSG 랜더스필드", "ILF", R.drawable.ic_sunny, 37.436, 126.693),
    CNP("창원 NC 파크", "CNP", R.drawable.ic_sunny, 35.222, 128.582),
    GCF("광주-기아 챔피언스 필드", "GCF", R.drawable.ic_sunny, 35.168, 126.889),
    BSJ("사직야구장", "BSJ", R.drawable.ic_sunny, 35.193, 129.061),
    BDP("대전 한화생명 이글스 파크", "DEP", R.drawable.ic_sunny, 36.316, 127.429),
    DSP("대구 삼성 라이온즈 파크", "DSP", R.drawable.ic_sunny, 35.841, 128.681),
    SOG("고척 스카이돔", "SOG", R.drawable.ic_sunny, 37.497, 126.867),
    UMS("울산 문수 야구장", "UMS", R.drawable.ic_sunny, 35.531, 129.265),
    POH("포항 야구장", "POH", R.drawable.ic_sunny, 36.007, 129.359),
    CJB("청주종합운동장 야구장", "CJB", R.drawable.ic_sunny, 36.638, 127.470),
    UNKNOWN("알수없음", "UNKNOWN", R.drawable.ic_sunny, 37.549, 126.991);

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