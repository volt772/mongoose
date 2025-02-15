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
 * DBP (대전 한화생명 볼파크)
 * DSP (대구 삼성 라이온즈 파크)
 * SOG (고척 스카이돔)
 * UMS (울산 문수 야구장)
 * POH (포항 야구장)
 * CJB (청주종합운동장 야구장)
 */
enum class Stadium(
    val signBoard: String,
    val code: String,
    @DrawableRes val teamColor: Int,
    val lat: Double,
    val lon: Double,
) {
    SOJ(
        "서울종합운동장 야구장",
        "SOJ",
        R.drawable.bg_team_dsb_lgt,
        37.512,
        127.071,
    ),
    SKW(
        "수원 케이티 위즈 파크",
        "SKW",
        R.drawable.bg_team_ktw,
        37.299,
        127.010
    ),
    ILF(
        "인천 SSG 랜더스필드",
        "ILF",
        R.drawable.bg_team_ssg,
        37.436,
        126.693
    ),
    CNP(
        "창원 NC 파크",
        "CNP",
        R.drawable.bg_team_ncd,
        35.222,
        128.582
    ),
    GCF(
        "광주-기아 챔피언스 필드",
        "GCF",
        R.drawable.bg_team_kat,
        35.168,
        126.889
    ),
    BSJ(
        "사직야구장",
        "BSJ",
        R.drawable.bg_team_ltg,
        35.193,
        129.061
    ),
    DBP(
        "대전 한화생명 볼파크",
        "DBP",
        R.drawable.bg_team_hhe,
        36.316,
        127.431
    ),
    DSP(
        "대구 삼성 라이온즈 파크",
        "DSP",
        R.drawable.bg_team_ssl,
        35.841,
        128.681
    ),
    SOG(
        "고척 스카이돔",
        "SOG",
        R.drawable.bg_team_kwh,
        37.497,
        126.867
    ),
    UMS(
        "울산 문수 야구장",
        "UMS",
        R.drawable.bg_team_ltg,
        35.531,
        129.265
    ),
    POH(
        "포항 야구장",
        "POH",
        R.drawable.bg_team_ssl,
        36.007,
        129.359
    ),
    CJB(
        "청주종합운동장 야구장",
        "CJB",
        R.drawable.bg_team_hhe,
        36.638,
        127.470
    ),
    NAN(
        "알수없는 야구장",
        "NAN",
        R.drawable.bg_team_nan,
        37.549,
        126.991
    );

    companion object {
        fun from(code: String): Stadium {
            return when(code) {
                "SOJ" -> SOJ
                "SKW" -> SKW
                "ILF" -> ILF
                "CNP" -> CNP
                "GCF" -> GCF
                "BSJ" -> BSJ
                "DBP" -> DBP
                "DSP" -> DSP
                "SOG" -> SOG
                "UMS" -> UMS
                "POH" -> POH
                "CJB" -> CJB
                else -> NAN
            }
        }
    }
}