package com.feri.murahjaya.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var auth: Auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Auth(context!!)

        initView()
        iniViewPager()
    }

    private fun initView() {
        userName.text = auth.getUserProfile().displayName
        userEmail.text = auth.getUserProfile().email

        if (auth.getUserProfile().photoUrl != null) {
            Glide.with(this).load(auth.getUserProfile().photoUrl).into(userImage)
        } else {
            userImage.setImageResource(R.drawable.ic_user_default)
        }

        if (auth.isUserVerified()) {
            verified.visibility = View.VISIBLE
        }
    }

    private fun iniViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteFragment(), "Favorit Anda")
        adapter.addFragment(SettingFragment(), "Pengaturan Akun")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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

    override fun onStart() {
        super.onStart()
        if (!Auth(context!!).isUserVerified()) {
            Snackbar.make(parentLayout, "Harap Verifikasi Email Anda", Snackbar.LENGTH_LONG)
                .setAction("Verifikasi"){ Auth(context!!).sendVerificationEmail() }
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }
}
