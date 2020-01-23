package com.feri.murahjaya.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.CategoryAdapter
import com.feri.murahjaya.adapter.ProductHorizontalAdapter
import com.feri.murahjaya.adapter.TrendingAdapter
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.LIST_TYPE
import com.feri.murahjaya.view.ProductListActivity
import kotlinx.android.synthetic.main.home_content.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var mAdapter: ProductHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = Firestore(context!!).getProductForHome()

        initList()

        bt_view_all.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra("state", LIST_TYPE.BYALL.ordinal)
            startActivity(intent)
        }
    }

    private fun initList() {
        categoryList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        categoryList.adapter = CategoryAdapter(context!!)

        trendingList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        trendingList.adapter = TrendingAdapter()

        productList.layoutManager = LinearLayoutManager(context!!, RecyclerView.HORIZONTAL, false)
        productList.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onResume() {
        super.onResume()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}
