package com.mindorks.framework.mvvm.ui.feed.blogs

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.databinding.ItemBlogViewBinding
import com.mindorks.framework.mvvm.databinding.ItemEmptyViewBinding
import com.mindorks.framework.mvvm.ui.base.BaseAdapter
import com.mindorks.framework.mvvm.utils.AppLogger

class BlogAdapter(
    private val onRetryClick: () -> Unit
) : BaseAdapter<BlogResponse.Blog, BlogAdapter.BlogViewHolder>(BlogDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return if (currentList.isEmpty()) 1 else currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = ItemBlogViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BlogViewHolder.NormalViewHolder(binding)
            }
            VIEW_TYPE_EMPTY -> {
                val binding = ItemEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BlogViewHolder.EmptyViewHolder(binding, onRetryClick)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder.NormalViewHolder -> {
                if (currentList.isNotEmpty()) {
                    holder.bind(currentList[position])
                }
            }
            is BlogViewHolder.EmptyViewHolder -> {
                // Empty view doesn't need binding
            }
        }
    }

    sealed class BlogViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        
        class NormalViewHolder(
            private val binding: ItemBlogViewBinding
        ) : BlogViewHolder(binding.root) {
            
            fun bind(blog: BlogResponse.Blog) {
                with(binding) {
                    titleTextView.text = blog.title
                    authorTextView.text = blog.author
                    dateTextView.text = blog.date
                    contentTextView.text = blog.description

                    blog.coverImgUrl?.let { imageUrl ->
                        Glide.with(coverImageView.context)
                            .load(imageUrl)
                            .centerCrop()
                            .into(coverImageView)
                    }

                    root.setOnClickListener {
                        blog.blogUrl?.let { url ->
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                root.context.startActivity(intent)
                            } catch (e: Exception) {
                                AppLogger.e("Error opening blog URL: ${e.message}")
                            }
                        }
                    }
                }
            }
        }

        class EmptyViewHolder(
            private val binding: ItemEmptyViewBinding,
            private val onRetryClick: () -> Unit
        ) : BlogViewHolder(binding.root) {
            
            init {
                binding.btnRetry.setOnClickListener { onRetryClick() }
            }
        }
    }

    private class BlogDiffCallback : DiffUtil.ItemCallback<BlogResponse.Blog>() {
        override fun areItemsTheSame(
            oldItem: BlogResponse.Blog,
            newItem: BlogResponse.Blog
        ): Boolean {
            return oldItem.blogUrl == newItem.blogUrl
        }

        override fun areContentsTheSame(
            oldItem: BlogResponse.Blog,
            newItem: BlogResponse.Blog
        ): Boolean {
            return oldItem == newItem
        }
    }
}