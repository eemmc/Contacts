package com.hbb.contacts.core.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter <T, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
    private var mItemList: ArrayList<Item<T>> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return if (mItemList.isNotEmpty()) {
            mItemList[position].type
        } else {
            0
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun getItem(position: Int): T? {
        return mItemList[position].data
    }

    protected fun updateInner(data: List<Item<T>>) {
        if (mItemList.isEmpty()) {
            mItemList.addAll(data)
            notifyDataSetChanged()
        } else {
            val result = DiffUtil.calculateDiff(
                ItemDiff(mItemList, data)
            )
            //
            mItemList.clear()
            mItemList.addAll(data)
            //
            result.dispatchUpdatesTo(this)
        }
    }

    protected open fun appendInner(news: List<Item<T>>) {
        val swap = ArrayList(mItemList)
        swap.addAll(news)
        //
        val result = DiffUtil.calculateDiff(
            ItemDiff(mItemList, swap)
        )
        //
        mItemList.clear()
        mItemList.addAll(swap)
        //
        result.dispatchUpdatesTo(this)
    }

    open class Item<T>(
        val data: T?,
        val type: Int = 0
    )

    private class ItemDiff<T>(
        private val oldList: List<Item<T>>,
        private val newList: List<Item<T>>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].type == newList[newItemPosition].type
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].data == newList[newItemPosition].data
        }
    }
}