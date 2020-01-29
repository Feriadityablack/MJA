package com.feri.murahjaya.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.utils.ADDRESSHANDLER
import com.feri.murahjaya.view.AddressActivity
import com.feri.murahjaya.view.OrderActivity
import com.feri.murahjaya.view.ProfileSettingActivity
import kotlinx.android.synthetic.main.setting_item.view.*

class SettingAdapter(private val context: Context) :
    RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    private val icons = listOf(
        R.drawable.ic_truck,
        R.drawable.ic_order,
        R.drawable.ic_wheel,
        R.drawable.ic_logout
    )

    private val titles = listOf(
        "Alamat Pengiriman",
        "Pesanan Anda",
        "Pengaturan Profil",
        "Sign Out"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.setting_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = icons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.settingImage.setImageResource(icons[position])
        holder.itemView.settingName.text = titles[position]

        holder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    val intent = Intent(context, AddressActivity::class.java)
                    intent.putExtra("handler", ADDRESSHANDLER.EDIT.ordinal)
                    context.startActivity(intent)
                }
                1 -> context.startActivity(Intent(context, OrderActivity::class.java))
                2 -> context.startActivity(Intent(context, ProfileSettingActivity::class.java))
                3 -> Auth(context).signOut()
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}