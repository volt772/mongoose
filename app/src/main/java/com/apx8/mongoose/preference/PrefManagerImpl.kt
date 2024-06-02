package com.apx8.mongoose.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.apx8.mongoose.domain.constants.Stadium
import javax.inject.Inject


/**
 * PrefManagerImpl
 */
class PrefManagerImpl @Inject constructor(
    private val context: Context,
) : PrefManager {

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setMyStadium(code: String) {
        preferences.edit().putString(MY_STADIUM, code).apply()
    }

    override fun getMyStadium(): String {
        return preferences.getString(MY_STADIUM, Stadium.NAN.code)?: Stadium.NAN.code
    }

    companion object {
        const val MY_STADIUM = "my_stadium"
    }
}
