package com.hbb.contacts.core.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.*
import com.hbb.contacts.core.ui.base.BaseActivity
import com.hbb.contacts.core.ui.base.BaseAdapter
import com.hbb.contacts.core.ui.callback.OnClickedByIdCallback
import com.hbb.contacts.core.ui.holder.*

class AccountActivity : BaseActivity() {
    private lateinit var binding: ActivityStandardsBinding


    private val itemClickedListener = object : OnClickedByIdCallback {
        override fun clicked(id: Int) {
            Log.e("DEBUG", "view client: $id")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStandardsBinding.inflate(layoutInflater)
        super.setContentView(binding.root)
        super.setSupportActionBar(binding.toolbar)
        super.setTitle(R.string.account_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ListAdapter(itemClickedListener)
        //

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private class Info(
        val title: String,
        val text: String? = null,
        val image: String? = null,
        val separator: Boolean = false
    )

    private class ListAdapter(
        private val itemCallback: OnClickedByIdCallback
    ) : BaseAdapter<Info, RecyclerView.ViewHolder>() {

        init {
            val items = ArrayList<Item<Info>>()
            //
            items.add(Item(null, 0))
            items.add(Item(Info("头像", image = null, separator = true), 1))
            items.add(Item(Info("昵称", "小明"), 2))
            items.add(Item(null, 0))
            items.add(Item(Info("修改手机号", separator = true), 3))
            items.add(Item(Info("修改密码", separator = true), 3))
            items.add(Item(Info("注销账户"), 3))
            items.add(Item(null, 0))
            items.add(Item(Info("退出登录"), 4))
            //
            updateInner(items)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                0 -> {
                    EmptySpaceHolder(
                        ItemEmptySpaceBinding.inflate(
                            inflater, parent, false
                        ).root
                    )
                }
                1 -> {
                    ModifyImageHolder(
                        ItemModifyImageBinding.inflate(inflater, parent, false),
                        itemCallback
                    )
                }
                2 -> {
                    ModifyInputHolder(
                        ItemModifyInputBinding.inflate(inflater, parent, false),
                        itemCallback
                    )
                }
                3 -> {
                    ModifyForwardHolder(
                        ItemModifyForwardBinding.inflate(inflater, parent, false),
                        itemCallback
                    )
                }
                4 -> {
                    SingleButtonHolder(
                        ItemSingleButtonBinding.inflate(inflater, parent, false),
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
                is ModifyImageHolder -> {
                    val item = getItem(position)!!
                    holder.bind(item.title, item.image, item.separator)
                }
                is ModifyInputHolder -> {
                    val item = getItem(position)!!
                    holder.bind(item.title, item.text, item.separator)
                }
                is ModifyForwardHolder -> {
                    val item = getItem(position)!!
                    holder.bind(item.title, item.separator)
                }
                is SingleButtonHolder -> {
                    val item = getItem(position)!!
                    holder.bind(item.title, item.separator)
                }
            }
        }
    }
}