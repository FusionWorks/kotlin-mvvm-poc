package com.mindorks.framework.mvvm.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindorks.framework.mvvm.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLoginScreenDisplayed() {
        onView(withId(R.id.et_email)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_server_login)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_google_login)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_fb_login)).check(matches(isDisplayed()))
    }

    @Test
    fun testLoginWithValidCredentials() {
        onView(withId(R.id.et_email)).perform(typeText("test@example.com"))
        onView(withId(R.id.et_password)).perform(typeText("password123"))
        onView(withId(R.id.btn_server_login)).perform(click())

        // Add assertions for successful login behavior
        // This would depend on your mock setup
    }

    @Test
    fun testGoogleLoginButton() {
        onView(withId(R.id.ib_google_login)).perform(click())
        // Add assertions for Google login flow
    }

    @Test
    fun testFacebookLoginButton() {
        onView(withId(R.id.ib_fb_login)).perform(click())
        // Add assertions for Facebook login flow
    }
}