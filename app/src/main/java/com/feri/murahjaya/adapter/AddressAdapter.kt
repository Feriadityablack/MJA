package com.feri.murahjaya.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Address
import com.feri.murahjaya.utils.ADDRESSHANDLER
import com.feri.murahjaya.view.AddressDetailActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.address_item.view.*

class AddressAdapter(private val context: Context, private val type: ADDRESSHANDLER, options: FirestoreRecyclerOptions<Address>) :
    FirestoreRecyclerAdapter<Address, AddressAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Address) {
        holder.bind(model)

        holder.itemView.setOnClickListener {
            if (type == ADDRESSHANDLER.EDIT) {
                val intent = Intent(context, AddressDetailActivity::class.java)
                intent.putExtra("address", model)
                context.startActivity(intent)
            } else {
                val activity = context as Activity
                val intent = Intent()
                intent.putExtra("address", model)
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(address: Address) {
            itemView.userName.text = address.name
            itemView.addressDetail.text = address.address
            itemView.addressKecamatanDesa.text = "${address.desa}, ${address.kecamatan}"
            itemView.phoneNumber.text = address.noTelp
        }

    }

}