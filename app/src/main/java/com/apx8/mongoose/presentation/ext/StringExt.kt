package com.apx8.mongoose.ext

import com.apx8.mongoose.MongooseApp.Companion.appContext

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


fun getStringRes(str: Int) = appContext.getString(str)

fun String.limitAndAbbr(limit: Int): String {
	return if (this.count() > limit) {
		this.substring(0, limit) + "..."
	} else {
		this
	}
}

fun randomKey(): String {
	val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	return (1..5)
		.map { (1..charPool.size).shuffled().last() }
		.map(charPool::get)
		.joinToString("")
}