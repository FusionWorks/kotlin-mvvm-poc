package com.mindorks.framework.mvvm.migration

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.ui.main.SwipeHelper
import com.mindorks.framework.mvvm.utils.ScreenUtils

/**
 * Migration utilities for PlaceHolderView to RecyclerView conversion
 */
object PlaceHolderViewMigration {

    /**
     * Configure RecyclerView to behave like SwipePlaceHolderView
     */
    fun configureAsSwipeView(
        context: Context,
        recyclerView: RecyclerView,
        onSwipe: (Int) -> Unit
    ) {
        // Setup swipe functionality
        val swipeHelper = SwipeHelper(onSwipe)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(recyclerView)

        // Configure layout to match SwipePlaceHolderView behavior
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)

        recyclerView.apply {
            // Set padding to center cards
            val paddingHorizontal = (screenWidth * 0.05).toInt()
            val paddingTop = (screenHeight * 0.02).toInt()
            setPadding(paddingHorizontal, paddingTop, paddingHorizontal, 0)

            // Disable scrolling for swipe-only behavior
            isNestedScrollingEnabled = false
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    /**
     * Calculate card dimensions similar to SwipePlaceHolderView
     */
    fun calculateCardDimensions(context: Context): Pair<Int, Int> {
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)

        val cardWidth = (screenWidth * 0.90).toInt()
        val cardHeight = (screenHeight * 0.75).toInt()

        return Pair(cardWidth, cardHeight)
    }
}