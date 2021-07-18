package com.hbb.contacts.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hbb.contacts.core.databinding.FragmentContactBinding
import com.hbb.contacts.core.ui.base.BaseFragment

class ContactFragment : BaseFragment() {
    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(
            inflater, container, false
        )
        //
        return binding.root
    }

    companion object {
        const val TAG_NAME = "ContactFragment"
    }
}