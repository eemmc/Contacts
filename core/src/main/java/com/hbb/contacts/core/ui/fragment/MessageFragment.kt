package com.hbb.contacts.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hbb.contacts.core.databinding.FragmentMessageBinding
import com.hbb.contacts.core.ui.base.BaseFragment

class MessageFragment : BaseFragment() {
    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(
            inflater, container, false
        )
        //
        return binding.root
    }

    companion object {
        const val TAG_NAME = "MessageFragment"
    }
}