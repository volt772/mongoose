package com.apx8.mongoose.presentation.ext

import com.apx8.mongoose.presentation.MongooseApp.Companion.appContext

/**
 * 문자열을 리스트로 분리
 */
fun String?.splitExt(delimiter: String?): List<String> {
	
	// 빈 리스트
	val emptyList = listOf<String>()
	
	if (this.isNullOrEmpty()) {
		// 구분자확인 null 인지 확인
		if (!delimiter.isNullOrEmpty()) {
			return emptyList
		}
	} else {
		// 구분자확인 null 인지 확인
		if (delimiter.isNullOrEmpty()) return emptyList
		
		return try {
			this.split(delimiter)
		} catch (e: Exception) {
			emptyList
		}
	}
	
	return emptyList
}