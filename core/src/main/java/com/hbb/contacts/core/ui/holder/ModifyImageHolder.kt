package com.hbb.contacts.core.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.ItemModifyImageBinding
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback

class ModifyImageHolder(
    private val binding: ItemModifyImageBinding,
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

    fun bind(title: String, image: String?, underline: Boolean) {
        binding.title.text = title
        binding.separator.visibility = if (underline) View.VISIBLE else View.GONE
        //
        Glide.with(binding.avatar)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.ic_person_24)
            .into(binding.avatar)
    }
}