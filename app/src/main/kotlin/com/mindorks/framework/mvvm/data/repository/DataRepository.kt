package com.mindorks.framework.mvvm.data.repository

import com.mindorks.framework.mvvm.data.local.database.entity.Option
import com.mindorks.framework.mvvm.data.local.database.entity.Question
import com.mindorks.framework.mvvm.data.local.database.entity.User
import com.mindorks.framework.mvvm.data.model.LoggedInMode
import com.mindorks.framework.mvvm.data.model.QuestionWithOptions
import com.mindorks.framework.mvvm.data.network.NetworkResult
import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.network.model.LoginResponse
import com.mindorks.framework.mvvm.data.network.model.LogoutResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    // User Management
    suspend fun insertUser(user: User): Long
    fun getAllUsers(): Flow<List<User>>
    suspend fun getUserById(userId: Long): User?

    // Question Management
    suspend fun insertQuestion(question: Question)
    suspend fun insertQuestions(questions: List<Question>)
    fun getAllQuestionsWithOptions(): Flow<List<QuestionWithOptions>>
    suspend fun isQuestionTableEmpty(): Boolean

    // Option Management
    suspend fun insertOption(option: Option)
    suspend fun insertOptions(options: List<Option>)
    suspend fun isOptionTableEmpty(): Boolean

    // Preferences
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

    // Network API calls
    suspend fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): NetworkResult<LoginResponse>
    suspend fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): NetworkResult<LoginResponse>
    suspend fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): NetworkResult<LoginResponse>
    suspend fun doLogoutApiCall(): NetworkResult<LogoutResponse>
    suspend fun getBlogApiCall(): NetworkResult<BlogResponse>
    suspend fun getOpenSourceApiCall(): NetworkResult<OpenSourceResponse>

    // Utility methods
    fun updateUserInfo(
        accessToken: String?,
        userId: Long?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        profilePicPath: String?
    )
    
    fun setUserAsLoggedOut()
    suspend fun seedDatabaseQuestions(): Boolean
    suspend fun seedDatabaseOptions(): Boolean
}