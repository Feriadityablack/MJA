package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.OrderAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.STATUS
import com.feri.murahjaya.view.fragment.order.*
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pesanan Anda"
        toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(WaitingFragment(), STATUS.WAITING.status)
        adapter.addFragment(PayedFragment(), STATUS.PAYED.status)
        adapter.addFragment(ProcessFragment(), STATUS.PROCESS.status)
        adapter.addFragment(DeliveryFragment(), STATUS.DELIVERY.status)
        adapter.addFragment(CompletedFragment(), STATUS.COMPLETED.status)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentList = mutableListOf<Fragment>()
        private val titleList = mutableListOf<String>()

        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence? = titleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

    }
}
