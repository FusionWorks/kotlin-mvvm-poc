package com.mindorks.framework.mvvm.data.local.prefs

import android.content.SharedPreferences
import com.mindorks.framework.mvvm.data.model.LoggedInMode
import com.mindorks.framework.mvvm.utils.AppConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesHelper {

    companion object {
        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return sharedPreferences.getInt(
            PREF_KEY_USER_LOGGED_IN_MODE,
            LoggedInMode.LOGGED_OUT.type
        )
    }

    override fun setCurrentUserLoggedInMode(mode: LoggedInMode) {
        sharedPreferences.edit()
            .putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.type)
            .apply()
    }

    override fun getCurrentUserId(): Long? {
        val userId = sharedPreferences.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX)
        return if (userId == AppConstants.NULL_INDEX) null else userId
    }

    override fun setCurrentUserId(userId: Long?) {
        val id = userId ?: AppConstants.NULL_INDEX
        sharedPreferences.edit()
            .putLong(PREF_KEY_CURRENT_USER_ID, id)
            .apply()
    }

    override fun getCurrentUserName(): String? {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_NAME, null)
    }

    override fun setCurrentUserName(userName: String?) {
        sharedPreferences.edit()
            .putString(PREF_KEY_CURRENT_USER_NAME, userName)
            .apply()
    }

    override fun getCurrentUserEmail(): String? {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
    }

    override fun setCurrentUserEmail(email: String?) {
        sharedPreferences.edit()
            .putString(PREF_KEY_CURRENT_USER_EMAIL, email)
            .apply()
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null)
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) {
        sharedPreferences.edit()
            .putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl)
            .apply()
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    override fun setAccessToken(accessToken: String?) {
        sharedPreferences.edit()
            .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}