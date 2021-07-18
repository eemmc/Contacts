package com.hbb.contacts.core.ui.fragment

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.FragmentDiscoverBinding
import com.hbb.contacts.core.databinding.ItemRedirectionBinding
import com.hbb.contacts.core.ui.activity.AccountActivity
import com.hbb.contacts.core.ui.activity.BrowserActivity
import com.hbb.contacts.core.ui.base.BaseAdapter
import com.hbb.contacts.core.ui.base.BaseFragment
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback
import com.hbb.contacts.core.ui.holder.EmptySpaceHolder
import com.hbb.contacts.core.ui.holder.RedirectionHolder

class DiscoverFragment : BaseFragment() {

    private lateinit var binding: FragmentDiscoverBinding

    private val itemClickedListener = object : OnClickedByIdCallback {
        override fun clicked(id: Int) {
            when (id) {
                0 -> {
                    startActivity(Intent(requireContext(), BrowserActivity::class.java).apply {
                        data = Uri.parse("https://www.baidu.com/")
                    })
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(
            inflater, container, false
        )
        //
        binding.toolbar.title = "小工具"
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(ListItemDecoration())
        binding.recyclerView.adapter = ListAdapter(itemClickedListener)
        //
        return binding.root
    }

    companion object {
        const val TAG_NAME = "DiscoverFragment"
    }

    private class Info(val id: Int, val value: String)

    private class ListAdapter(
        private val itemCallback: OnClickedByIdCallback
    ) : BaseAdapter<Info, RecyclerView.ViewHolder>() {

        init {
            val items = ArrayList<Item<Info>>()
            //
            items.add(Item(Info(0, "浏览器"), 1))
            items.add(Item(Info(1, "首页列表"), 1))
            items.add(Item(Info(2, "文件选择器"), 1))
            //
            updateInner(items)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                1 -> {
                    RedirectionHolder(
                        ItemRedirectionBinding.inflate(inflater, parent, false),
                        itemCallback
                    )
                }
                else -> {
                    EmptySpaceHolder(View(inflater.context))
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is RedirectionHolder -> {
                    val item = getItem(position)
                    holder.bind(item!!.value, underline = false)
                }
            }
        }
    }

    private class ListItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val padding = parent.resources.getDimensionPixelSize(R.dimen.d8)
            outRect.set(0, padding, 0, 0)
        }
    }
}