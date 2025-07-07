package com.mindorks.framework.mvvm.ui.feed.opensource

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import com.mindorks.framework.mvvm.databinding.ItemEmptyViewBinding
import com.mindorks.framework.mvvm.databinding.ItemRepoViewBinding
import com.mindorks.framework.mvvm.ui.base.BaseAdapter

class OpenSourceAdapter(
    private val onRetryClick: () -> Unit
) : BaseAdapter<OpenSourceResponse.Repo, OpenSourceAdapter.RepoViewHolder>(RepoDiffCallback()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = ItemRepoViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RepoViewHolder.NormalViewHolder(binding)
            }
            VIEW_TYPE_EMPTY -> {
                val binding = ItemEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RepoViewHolder.EmptyViewHolder(binding, onRetryClick)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        when (holder) {
            is RepoViewHolder.NormalViewHolder -> {
                if (currentList.isNotEmpty()) {
                    holder.bind(currentList[position])
                }
            }
            is RepoViewHolder.EmptyViewHolder -> {
                // Empty view doesn't need binding
            }
        }
    }

    sealed class RepoViewHolder(itemView: View) : BaseViewHolder<OpenSourceResponse.Repo, ViewBinding>(itemView as ViewBinding) {
        
        class NormalViewHolder(
            override val binding: ItemRepoViewBinding
        ) : RepoViewHolder(binding.root) {
            
            override fun bind(repo: OpenSourceResponse.Repo) {
                with(binding) {
                    titleTextView.text = repo.title
                    contentTextView.text = repo.description

                    repo.coverImgUrl?.let { imageUrl ->
                        Glide.with(coverImageView.context)
                            .load(imageUrl)
                            .centerCrop()
                            .into(coverImageView)
                    }

                    root.setOnClickListener {
                        repo.projectUrl?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            root.context.startActivity(intent)
                        }
                    }
                }
            }
        }

        class EmptyViewHolder(
            override val binding: ItemEmptyViewBinding,
            private val onRetryClick: () -> Unit
        ) : RepoViewHolder(binding.root) {
            override fun bind(item: OpenSourceResponse.Repo) {

            }

            init {
                binding.btnRetry.setOnClickListener { onRetryClick() }
            }
        }
    }

    class RepoDiffCallback : DiffUtil.ItemCallback<OpenSourceResponse.Repo>() {
        override fun areItemsTheSame(
            oldItem: OpenSourceResponse.Repo,
            newItem: OpenSourceResponse.Repo
        ): Boolean {
            return oldItem.projectUrl == newItem.projectUrl
        }

        override fun areContentsTheSame(
            oldItem: OpenSourceResponse.Repo,
            newItem: OpenSourceResponse.Repo
        ): Boolean {
            return oldItem == newItem
        }
    }
}