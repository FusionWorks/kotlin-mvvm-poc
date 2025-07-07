package com.mindorks.framework.mvvm.ui.feed

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActivityFeedBinding
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : BaseActivity<ActivityFeedBinding>() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var pagerAdapter: FeedPagerAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FeedActivity::class.java)
        }
    }

    override fun getViewBinding(): ActivityFeedBinding {
        return ActivityFeedBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter = FeedPagerAdapter(this)
        binding.feedViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.feedViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.blog)
                1 -> getString(R.string.open_source)
                else -> ""
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val upIntent = NavUtils.getParentActivityIntent(this)
                upIntent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities()
                } else {
                    NavUtils.navigateUpTo(this, upIntent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}