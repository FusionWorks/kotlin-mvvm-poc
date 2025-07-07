package com.mindorks.framework.mvvm.data.local.prefs

import com.mindorks.framework.mvvm.data.model.LoggedInMode

interface PreferencesHelper {

    fun getCurrentUserLoggedInMode(): Int

    fun setCurrentUserLoggedInMode(mode: LoggedInMode)

    fun getCurrentUserId(): Long?

    fun setCurrentUserId(userId: Long?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(userName: String?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getCurrentUserProfilePicUrl(): String?

    fun setCurrentUserProfilePicUrl(profilePicUrl: String?)

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun clearPreferences()
}