package com.feri.murahjaya.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.ProductGridAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.createToast
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var adapter: ProductGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Firestore(context!!).getCurrentUserFavorite(emptyView)
        favoriteList.layoutManager = GridLayoutManager(context, 2 , RecyclerView.VERTICAL, false)
        favoriteList.adapter = adapter

        if (adapter.snapshots.isNotEmpty()) {
            createToast(context!!, "Anda belum memiliki produk favorit")
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

}
