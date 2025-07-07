package com.mindorks.framework.mvvm.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mindorks.framework.mvvm.data.local.database.dao.OptionDao
import com.mindorks.framework.mvvm.data.local.database.dao.QuestionDao
import com.mindorks.framework.mvvm.data.local.database.dao.UserDao
import com.mindorks.framework.mvvm.data.local.database.entity.Option
import com.mindorks.framework.mvvm.data.local.database.entity.Question
import com.mindorks.framework.mvvm.data.local.database.entity.User
import com.mindorks.framework.mvvm.data.local.prefs.PreferencesHelper
import com.mindorks.framework.mvvm.data.model.LoggedInMode
import com.mindorks.framework.mvvm.data.model.QuestionWithOptions
import com.mindorks.framework.mvvm.data.network.ApiHelper
import com.mindorks.framework.mvvm.data.network.NetworkResult
import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.network.model.LoginResponse
import com.mindorks.framework.mvvm.data.network.model.LogoutResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import com.mindorks.framework.mvvm.utils.AppConstants
import com.mindorks.framework.mvvm.utils.CommonUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDao: UserDao,
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val preferencesHelper: PreferencesHelper,
    private val apiHelper: ApiHelper
) : DataRepository {

    // User Management
    override suspend fun insertUser(user: User): Long = userDao.insertUser(user)

    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override suspend fun getUserById(userId: Long): User? = userDao.getUserById(userId)

    // Question Management
    override suspend fun insertQuestion(question: Question) = questionDao.insertQuestion(question)

    override suspend fun insertQuestions(questions: List<Question>) = 
        questionDao.insertQuestions(questions)

    override fun getAllQuestionsWithOptions(): Flow<List<QuestionWithOptions>> = 
        questionDao.getAllQuestionsWithOptions()

    override suspend fun isQuestionTableEmpty(): Boolean = questionDao.getQuestionCount() == 0

    // Option Management
    override suspend fun insertOption(option: Option) = optionDao.insertOption(option)

    override suspend fun insertOptions(options: List<Option>) = optionDao.insertOptions(options)

    override suspend fun isOptionTableEmpty(): Boolean = optionDao.getOptionCount() == 0

    // Preferences
    override fun getCurrentUserLoggedInMode(): Int = preferencesHelper.getCurrentUserLoggedInMode()

    override fun setCurrentUserLoggedInMode(mode: LoggedInMode) = 
        preferencesHelper.setCurrentUserLoggedInMode(mode)

    override fun getCurrentUserId(): Long? = preferencesHelper.getCurrentUserId()

    override fun setCurrentUserId(userId: Long?) = preferencesHelper.setCurrentUserId(userId)

    override fun getCurrentUserName(): String? = preferencesHelper.getCurrentUserName()

    override fun setCurrentUserName(userName: String?) = 
        preferencesHelper.setCurrentUserName(userName)

    override fun getCurrentUserEmail(): String? = preferencesHelper.getCurrentUserEmail()

    override fun setCurrentUserEmail(email: String?) = preferencesHelper.setCurrentUserEmail(email)

    override fun getCurrentUserProfilePicUrl(): String? = 
        preferencesHelper.getCurrentUserProfilePicUrl()

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) = 
        preferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl)

    override fun getAccessToken(): String? = preferencesHelper.getAccessToken()

    override fun setAccessToken(accessToken: String?) = 
        preferencesHelper.setAccessToken(accessToken)

    // Network API calls
    override suspend fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): NetworkResult<LoginResponse> = 
        apiHelper.doServerLoginApiCall(request)

    override suspend fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): NetworkResult<LoginResponse> = 
        apiHelper.doGoogleLoginApiCall(request)

    override suspend fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): NetworkResult<LoginResponse> = 
        apiHelper.doFacebookLoginApiCall(request)

    override suspend fun doLogoutApiCall(): NetworkResult<LogoutResponse> = 
        apiHelper.doLogoutApiCall()

    override suspend fun getBlogApiCall(): NetworkResult<BlogResponse> = 
        apiHelper.getBlogApiCall()

    override suspend fun getOpenSourceApiCall(): NetworkResult<OpenSourceResponse> = 
        apiHelper.getOpenSourceApiCall()

    // Utility methods
    override fun updateUserInfo(
        accessToken: String?,
        userId: Long?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        profilePicPath: String?
    ) {
        setAccessToken(accessToken)
        setCurrentUserId(userId)
        setCurrentUserLoggedInMode(loggedInMode)
        setCurrentUserName(userName)
        setCurrentUserEmail(email)
        setCurrentUserProfilePicUrl(profilePicPath)
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(
            accessToken = null,
            userId = null,
            loggedInMode = LoggedInMode.LOGGED_OUT,
            userName = null,
            email = null,
            profilePicPath = null
        )
    }

    override suspend fun seedDatabaseQuestions(): Boolean {
        return try {
            if (isQuestionTableEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<Question>>() {}.type
                val questionsJson = CommonUtils.loadJSONFromAsset(
                    context,
                    AppConstants.SEED_DATABASE_QUESTIONS
                )
                val questions: List<Question> = gson.fromJson(questionsJson, type)
                insertQuestions(questions)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun seedDatabaseOptions(): Boolean {
        return try {
            if (isOptionTableEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<Option>>() {}.type
                val optionsJson = CommonUtils.loadJSONFromAsset(
                    context,
                    AppConstants.SEED_DATABASE_OPTIONS
                )
                val options: List<Option> = gson.fromJson(optionsJson, type)
                insertOptions(options)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}