package com.hbb.contacts.core.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.ItemRedirectionBinding
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback

class RedirectionHolder(
    private val binding: ItemRedirectionBinding,
    private val callback: OnClickedByIdCallback
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callback.clicked(this.adapterPosition)
        }
        binding.root.setBackgroundResource(
            R.drawable.item_center_selector
        )
    }

    fun bind(title: String, underline: Boolean) {
        binding.title.text = title
        binding.separator.visibility = if (underline) View.VISIBLE else View.GONE
    }
}