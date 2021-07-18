package com.hbb.contacts.core.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.databinding.ItemMineHeaderBinding
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback

class MineHeaderHolder(
    binding: ItemMineHeaderBinding,
    callback: OnClickedByIdCallback
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.mineItemAccountInfo.setOnClickListener {
            callback.clicked(it.id)
        }
    }
}