package com.feri.murahjaya.view.fragment.order


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.OrderAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.STATUS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_order.*

/**
 * A simple [Fragment] subclass.
 */
class WaitingFragment : Fragment() {

    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = Firestore(context!!)
        val mUser = FirebaseAuth.getInstance().currentUser!!

        val query = firestore.orderRef.whereEqualTo("user.uid", mUser.uid)
            .whereEqualTo("status", STATUS.WAITING).orderBy("createdAt", Query.Direction.ASCENDING)

        adapter = firestore.getAllOrderByStatus(query)
        orderList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        orderList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}
