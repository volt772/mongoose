package com.apx8.mongoose.presentation.ext

/**
 * 문자열을 리스트로 분리
 */
fun String?.splitExt(delimiter: String?): List<String> {
	
	val emptyList = listOf<String>()
	
	if (this.isNullOrEmpty()) {
		if (!delimiter.isNullOrEmpty()) {
			return emptyList
		}
	} else {
		if (delimiter.isNullOrEmpty()) return emptyList
		
		return try {
			this.split(delimiter)
		} catch (e: Exception) {
			emptyList
		}
	}
	
	return emptyList
}