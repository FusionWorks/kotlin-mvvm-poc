package com.mindorks.framework.mvvm.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.BuildConfig
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActivityMainBinding
import com.mindorks.framework.mvvm.ui.about.AboutFragment
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.feed.FeedActivity
import com.mindorks.framework.mvvm.ui.login.LoginActivity
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var questionAdapter: QuestionCardAdapter
    private lateinit var swipeHelper: SwipeHelper

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setSupportActionBar(binding.toolbar)
        setupNavigationDrawer()
        setupRecyclerView()
        observeViewModel()
        updateAppVersion()
    }

    private fun setupNavigationDrawer() {
        val toggle = androidx.appcompat.app.ActionBarDrawerToggle(
            this,
            binding.drawerView,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerView.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerView.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.nav_item_about -> showAboutFragment()
                R.id.nav_item_rate_us -> showRateUsDialog()
                R.id.nav_item_feed -> openFeedActivity()
                R.id.nav_item_logout -> viewModel.onLogoutClick()
            }
            true
        }
    }

    private fun setupRecyclerView() {
        questionAdapter = QuestionCardAdapter { position, optionIndex ->
            viewModel.onOptionClicked(position, optionIndex)
        }

        swipeHelper = SwipeHelper { position ->
            viewModel.onCardSwiped(position)
        }

        with(binding.cardsContainer) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = questionAdapter
            ItemTouchHelper(swipeHelper).attachToRecyclerView(this)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.questions.collect { questions ->
                questionAdapter.submitList(questions)
                if (questions.isEmpty()) {
                    // Add animation when reloading questions
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel.refreshQuestions()
                        animateCardContainer()
                    }, 800)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.userName.collect { name ->
                name?.let { updateUserName(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.userEmail.collect { email ->
                email?.let { updateUserEmail(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.userProfilePic.collect { profilePicUrl ->
                profilePicUrl?.let { updateUserProfilePic(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    onError(it)
                    viewModel.clearError()
                }
            }
        }
    }

    private fun animateCardContainer() {
        val animation = ScaleAnimation(
            1.15f, 1f, 1.15f, 1f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
        }
        binding.cardsContainer.startAnimation(animation)
    }

    private fun updateAppVersion() {
        val version = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        binding.navigationView.findViewById<android.widget.TextView>(R.id.tv_app_version)?.text = version
    }

    private fun updateUserName(name: String) {
        val headerView = binding.navigationView.getHeaderView(0)
        headerView.findViewById<android.widget.TextView>(R.id.tv_name)?.text = name
    }

    private fun updateUserEmail(email: String) {
        val headerView = binding.navigationView.getHeaderView(0)
        headerView.findViewById<android.widget.TextView>(R.id.tv_email)?.text = email
    }

    private fun updateUserProfilePic(profilePicUrl: String) {
        val headerView = binding.navigationView.getHeaderView(0)
        val profileImageView = headerView.findViewById<android.widget.ImageView>(R.id.iv_profile_pic)
        profileImageView?.let {
            Glide.with(this)
                .load(profilePicUrl)
                .circleCrop()
                .into(it)
        }
    }

    private fun showAboutFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.cl_root_view, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
        lockDrawer()
    }

    private fun showRateUsDialog() {
        RateUsDialog.newInstance().show(supportFragmentManager)
    }

    private fun openFeedActivity() {
        startActivity(FeedActivity.getStartIntent(this))
    }

    private fun lockDrawer() {
        binding.drawerView.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun unlockDrawer() {
        binding.drawerView.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun onFragmentDetached(tag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(it)
                .commit()
            unlockDrawer()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment != null) {
            onFragmentDetached(AboutFragment.TAG)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cut,
            R.id.action_copy,
            R.id.action_share,
            R.id.action_delete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openActivityOnTokenExpire() {
        startActivity(LoginActivity.getStartIntent(this))
        finish()
    }
}