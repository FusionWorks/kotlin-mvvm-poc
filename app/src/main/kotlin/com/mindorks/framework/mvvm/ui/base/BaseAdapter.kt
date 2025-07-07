package com.mindorks.framework.mvvm.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base adapter class for RecyclerView with ListAdapter and ViewBinding support
 */
abstract class BaseAdapter<T : Any, VH : BaseAdapter.BaseViewHolder<T, *>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    abstract class BaseViewHolder<T, VB : ViewBinding>(
        protected val binding: VB
    ) : RecyclerView.ViewHolder(binding.root) {
        
        abstract fun bind(item: T)
    }
}