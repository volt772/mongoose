package com.apx8.mongoose.domain.constants

enum class TranslatedDescription(val desc: String) {

    THUNDERSTORM_200("비를 동반한 천둥구름"),
    THUNDERSTORM_201("가벼운 비를 동반한 천둥구름"),
    THUNDERSTORM_202("폭우를 동반한 천둥구름"),
    THUNDERSTORM_210("약한 천둥구름"),
    THUNDERSTORM_211("천둥구름"),
    THUNDERSTORM_212("강한 천둥구름"),
    THUNDERSTORM_221("불규칙적 천둥구름"),
    THUNDERSTORM_230("약한 연무를 동반한 천둥구름"),
    THUNDERSTORM_231("연무를 동반한 천둥구름"),
    THUNDERSTORM_232("강한 안개비를 동반한 천둥구름"),

    DRIZZLE_300("가벼운 안개비"),
    DRIZZLE_301("안개비"),
    DRIZZLE_302("강한 안개비"),
    DRIZZLE_310("가벼운 적은비"),
    DRIZZLE_311("적은비"),
    DRIZZLE_312("강한 적은비"),
    DRIZZLE_313("소나기와 안개비"),
    DRIZZLE_314("강한 소나기와 안개비"),
    DRIZZLE_321("소나기"),

    RAIN_500("악한 비"),
    RAIN_501("중간 비"),
    RAIN_502("강한 비"),
    RAIN_503("매우 강한 비"),
    RAIN_504("극심한 비"),
    RAIN_511("우박"),
    RAIN_520("약한 소나기 비"),
    RAIN_521("소나기 비"),
    RAIN_522("강한 소나기 비"),
    RAIN_531("불규칙적 소나기 비"),

    SNOW_600("가끔 눈"),          // 가벼운 눈
    SNOW_601("눈"),
    SNOW_602("강한 눈"),
    SNOW_611("진눈깨비"),
    SNOW_612("소나기 진눈깨비"),
    SNOW_615("약한 비, 눈"),
    SNOW_616("비, 눈"),
    SNOW_620("약한 소나기, 눈"),
    SNOW_621("소나기, 눈"),
    SNOW_622("강한 소나기, 눈"),

    ATMOSPHERE_701("박무"),
    ATMOSPHERE_711("연기"),
    ATMOSPHERE_721("연무"),
    ATMOSPHERE_731("모래 먼지"),
    ATMOSPHERE_741("안개"),
    ATMOSPHERE_751("모래"),
    ATMOSPHERE_761("먼지"),
    ATMOSPHERE_762("화산재"),
    ATMOSPHERE_771("돌풍"),
    ATMOSPHERE_781("토네이도"),

    CLEAR_800("맑음"),                  // 구름 한 점 없는 맑은 하늘

    CLOUDS_801("구름조금"),             // 약간의 구름이 낀 하늘
    CLOUDS_802("구름조금"),             // 드문드문 구름이 낀 하늘
    CLOUDS_803("구름없음"),             // 구름이 거의 없는 하늘
    CLOUDS_804("구름많음"),             // 구름으로 뒤덮인 흐린 하늘

    NAN("알수없음");

    companion object {
        fun from(code: Int): TranslatedDescription {
            return when(code) {
                200 -> THUNDERSTORM_200
                201 -> THUNDERSTORM_201
                202 -> THUNDERSTORM_202
                210 -> THUNDERSTORM_210
                211 -> THUNDERSTORM_211
                212 -> THUNDERSTORM_212
                221 -> THUNDERSTORM_221
                230 -> THUNDERSTORM_230
                231 -> THUNDERSTORM_231
                232 -> THUNDERSTORM_232
                300 -> DRIZZLE_300
                301 -> DRIZZLE_301
                302 -> DRIZZLE_302
                310 -> DRIZZLE_310
                311 -> DRIZZLE_311
                312 -> DRIZZLE_312
                313 -> DRIZZLE_313
                314 -> DRIZZLE_314
                321 -> DRIZZLE_321
                500 -> RAIN_500
                501 -> RAIN_501
                502 -> RAIN_502
                503 -> RAIN_503
                504 -> RAIN_504
                511 -> RAIN_511
                520 -> RAIN_520
                521 -> RAIN_521
                522 -> RAIN_522
                531 -> RAIN_531
                600 -> SNOW_600
                601 -> SNOW_601
                602 -> SNOW_602
                611 -> SNOW_611
                612 -> SNOW_612
                615 -> SNOW_615
                616 -> SNOW_616
                620 -> SNOW_620
                621 -> SNOW_621
                622 -> SNOW_622
                701 -> ATMOSPHERE_701
                711 -> ATMOSPHERE_711
                721 -> ATMOSPHERE_721
                731 -> ATMOSPHERE_731
                741 -> ATMOSPHERE_741
                751 -> ATMOSPHERE_751
                761 -> ATMOSPHERE_761
                762 -> ATMOSPHERE_762
                771 -> ATMOSPHERE_771
                781 -> ATMOSPHERE_781
                800 -> CLEAR_800
                801 -> CLOUDS_801
                802 -> CLOUDS_802
                803 -> CLOUDS_803
                804 -> CLOUDS_804
                else -> NAN
            }
        }
    }
}



// 참고)
//201: "가벼운 비를 동반한 천둥구름",
//200: "비를 동반한 천둥구름",
//202: "폭우를 동반한 천둥구름",
//210: "약한 천둥구름",
//211: "천둥구름",
//212: "강한 천둥구름",
//221: "불규칙적 천둥구름",
//230: "약한 연무를 동반한 천둥구름",
//231: "연무를 동반한 천둥구름",
//232: "강한 안개비를 동반한 천둥구름",
//300: "가벼운 안개비",
//301: "안개비",
//302: "강한 안개비",
//310: "가벼운 적은비",
//311: "적은비",
//312: "강한 적은비",
//313: "소나기와 안개비",
//314: "강한 소나기와 안개비",
//321: "소나기",
//500: "악한 비",
//501: "중간 비",
//502: "강한 비",
//503: "매우 강한 비",
//504: "극심한 비",
//511: "우박",
//520: "약한 소나기 비",
//521: "소나기 비",
//522: "강한 소나기 비",
//531: "불규칙적 소나기 비",
//600: "가벼운 눈",
//601: "눈",
//602: "강한 눈",
//611: "진눈깨비",
//612: "소나기 진눈깨비",
//615: "약한 비와 눈",
//616: "비와 눈",
//620: "약한 소나기 눈",
//621: "소나기 눈",
//622: "강한 소나기 눈",
//701: "박무",
//711: "연기",
//721: "연무",
//731: "모래 먼지",
//741: "안개",
//751: "모래",
//761: "먼지",
//762: "화산재",
//771: "돌풍",
//781: "토네이도",
//800: "구름 한 점 없는 맑은 하늘",
//801: "약간의 구름이 낀 하늘",
//802: "드문드문 구름이 낀 하늘",
//803: "구름이 거의 없는 하늘",
//804: "구름으로 뒤덮인 흐린 하늘",