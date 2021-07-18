package com.hbb.contacts.core.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.ItemMineItemBinding
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback

class MineItemHolder(
    private val binding: ItemMineItemBinding,
    callback: OnClickedByIdCallback
) : RecyclerView.ViewHolder(binding.root) {
    private var itemViewId: Int = View.NO_ID

    init {
        binding.root.setOnClickListener {
            callback.clicked(itemViewId)
        }
    }

    fun bind(title: String?, id: Int = View.NO_ID, dir: Int = 1) {
        this.itemViewId = id
        binding.title.text = title
        when (dir) {
            0 -> {
                binding.root.setBackgroundResource(R.drawable.item_top_selector)
            }
            1 -> {
                binding.root.setBackgroundResource(R.drawable.item_center_selector)
            }
            2 -> {
                binding.root.setBackgroundResource(R.drawable.item_bottom_selector)
            }
        }
    }

}