package com.feri.murahjaya.view

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.SearchAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: SearchAdapter
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        adapter = SearchAdapter(this)

        initRecyclerViewItem()
    }

    private fun initRecyclerViewItem() {
        productList.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        productList.adapter = adapter

        FirebaseFirestore.getInstance().collection("products").get().addOnSuccessListener { documents ->
            val products = documents.toObjects(Product::class.java)

            adapter.addItems(products)
        }
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                productList.visibility = View.VISIBLE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                productList.visibility = View.VISIBLE
                return false
            }

        })

        super.onOptionsMenuClosed(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!searchView?.isIconified!!) {
            searchView?.isIconified = true
        }
    }
}
