package com.hbb.contacts.core.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.databinding.ItemMineGroupBinding
import com.hbb.contacts.core.databinding.ItemMineHeaderBinding

class MineGroupHolder(
    private val binding: ItemMineGroupBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String?) {
        binding.title.text = title
    }
}