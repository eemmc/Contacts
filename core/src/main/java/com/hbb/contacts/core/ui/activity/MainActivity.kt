package com.hbb.contacts.core.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.hbb.contacts.core.R
import com.hbb.contacts.core.databinding.ActivityMainBinding
import com.hbb.contacts.core.ui.base.BaseActivity
import com.hbb.contacts.core.ui.fragment.ContactFragment
import com.hbb.contacts.core.ui.fragment.DiscoverFragment
import com.hbb.contacts.core.ui.fragment.MessageFragment
import com.hbb.contacts.core.ui.fragment.MineFragment
import com.hbb.contacts.core.ui.widget.NavigationBar

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private var messageFragment: MessageFragment? = null
    private var contactFragment: ContactFragment? = null
    private var discoverFragment: DiscoverFragment? = null
    private var personFragment: MineFragment? = null

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        super.setIntent(intent)
        onHandleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(binding.root)
        //
        initNavigationBar(binding.mainNavigationBar)
        //
        val manager = supportFragmentManager
        if (savedInstanceState != null) {
            messageFragment = manager.getFragment(
                savedInstanceState,
                MessageFragment.TAG_NAME
            ) as MessageFragment
            contactFragment = manager.getFragment(
                savedInstanceState,
                ContactFragment.TAG_NAME
            ) as ContactFragment
            discoverFragment = manager.getFragment(
                savedInstanceState,
                DiscoverFragment.TAG_NAME
            ) as DiscoverFragment
            personFragment = manager.getFragment(
                savedInstanceState,
                MineFragment.TAG_NAME
            ) as MineFragment
        } else {
            messageFragment = MessageFragment()
            contactFragment = ContactFragment()
            discoverFragment = DiscoverFragment()
            personFragment = MineFragment()
            //
            val transaction = manager.beginTransaction()
            transaction.add(R.id.fragment_container, messageFragment!!, MessageFragment.TAG_NAME)
            transaction.add(R.id.fragment_container, contactFragment!!, ContactFragment.TAG_NAME)
            transaction.add(R.id.fragment_container, discoverFragment!!, DiscoverFragment.TAG_NAME)
            transaction.add(R.id.fragment_container, personFragment!!, MineFragment.TAG_NAME)
            transaction.commit()
            //
            binding.mainNavigationBar.check(R.id.item_main_tab_message)
        }
        //
        onHandleIntent(this.intent)
        //

    }

    private fun initNavigationBar(bar: NavigationBar) {
        bar.setOnCheckedChangeListener { _, id ->
            onMainTabPageChanged(id)
        }
        //
        val colors = ResourcesCompat.getColorStateList(
            resources, R.color.primary_selector, theme
        )!!
        //
        bar.addItem(
            R.id.item_main_tab_message,
            R.drawable.ic_message_24,
            getString(R.string.main_tab_message),
            colors
        )
        //
        bar.addItem(
            R.id.item_main_tab_contact,
            R.drawable.ic_contact_24,
            getString(R.string.main_tab_contact),
            colors
        )
        //
        bar.addItem(
            R.id.item_main_tab_discover,
            R.drawable.ic_discover_24,
            getString(R.string.main_tab_discover),
            colors
        )
        //
        bar.addItem(
            R.id.item_main_tab_mine,
            R.drawable.ic_person_24,
            getString(R.string.main_tab_person),
            colors
        )
    }

    private fun onMainTabPageChanged(id: Int) {
        when (id) {
            R.id.item_main_tab_message -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.show(messageFragment!!)
                transaction.hide(contactFragment!!)
                transaction.hide(discoverFragment!!)
                transaction.hide(personFragment!!)
                transaction.commit()
            }
            R.id.item_main_tab_contact -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.hide(messageFragment!!)
                transaction.show(contactFragment!!)
                transaction.hide(discoverFragment!!)
                transaction.hide(personFragment!!)
                transaction.commit()
            }
            R.id.item_main_tab_discover -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.hide(messageFragment!!)
                transaction.hide(contactFragment!!)
                transaction.show(discoverFragment!!)
                transaction.hide(personFragment!!)
                transaction.commit()
            }
            R.id.item_main_tab_mine -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.hide(messageFragment!!)
                transaction.hide(contactFragment!!)
                transaction.hide(discoverFragment!!)
                transaction.show(personFragment!!)
                transaction.commit()
            }
        }
    }

    private fun onHandleIntent(action: Intent?) {
        Log.e("DEBUG", "intent: $action")
    }
}