package com.mindorks.framework.mvvm.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragment
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragment

class FeedPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlogFragment.newInstance()
            1 -> OpenSourceFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}