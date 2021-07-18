package com.hbb.contacts.core.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.*
import com.hbb.contacts.core.ui.activity.AccountActivity
import com.hbb.contacts.core.ui.base.BaseAdapter
import com.hbb.contacts.core.ui.base.BaseFragment
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback
import com.hbb.contacts.core.ui.holder.EmptySpaceHolder
import com.hbb.contacts.core.ui.holder.MineGroupHolder
import com.hbb.contacts.core.ui.holder.MineHeaderHolder
import com.hbb.contacts.core.ui.holder.MineItemHolder

class MineFragment : BaseFragment() {

    private lateinit var binding: FragmentMineBinding

    private val itemClickedListener = object : OnClickedByIdCallback {
        override fun clicked(id: Int) {
            when (id) {
                R.id.mine_item_account_info -> {
                    startActivity(Intent(requireContext(), AccountActivity::class.java))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = MineAdapter(itemClickedListener)
        //
        return binding.root
    }

    companion object {
        const val TAG_NAME = "PersonFragment"
    }

    private class Info(val id: Int, val value: String?)

    private class MineAdapter(
        private val itemCallback: OnClickedByIdCallback
    ) : BaseAdapter<Info, RecyclerView.ViewHolder>() {

        init {
            val items = ArrayList<Item<Info>>()
            //
            items.add(Item(null, 0))
            items.add(Item(Info(0, "消息通知"), 3))
            items.add(Item(Info(0, "隐私设置"), 3))
            items.add(Item(null, 1))
            items.add(Item(Info(0, "意见反馈"), 3))
            items.add(Item(Info(0, "检查更新"), 3))
            items.add(Item(Info(0, "关于"), 3))
            //
            updateInner(items)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                0 -> {
                    MineHeaderHolder(
                        ItemMineHeaderBinding.inflate(
                            inflater, parent, false
                        ),
                        itemCallback
                    )
                }
                1 -> {
                    EmptySpaceHolder(
                        ItemEmptySpaceBinding.inflate(
                            inflater, parent, false
                        ).root
                    )
                }
                2 -> {
                    MineGroupHolder(
                        ItemMineGroupBinding.inflate(
                            inflater, parent, false
                        )
                    )
                }
                3 -> {
                    MineItemHolder(
                        ItemMineItemBinding.inflate(
                            inflater, parent, false
                        ),
                        itemCallback
                    )
                }
                else -> {
                    EmptySpaceHolder(View(inflater.context))
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is MineGroupHolder) {
                val item = getItem(position)!!
                holder.bind(item.value)
            } else if (holder is MineItemHolder) {
                val item = getItem(position)!!
                holder.bind(item.value, item.id)
            }
        }


    }
}