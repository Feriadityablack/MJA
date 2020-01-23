package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.feri.murahjaya.R
import com.feri.murahjaya.view.fragment.CartFragment
import com.feri.murahjaya.view.fragment.HomeFragment
import com.feri.murahjaya.view.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private val homeFragment = HomeFragment()
        private val cartFragment = CartFragment()
        private val profileFragment = ProfileFragment()
        private var activeFragment: Fragment = homeFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Murah Jaya"
        supportActionBar?.subtitle = "Temukan furniture unik"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_container, activeFragment).commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->
        when (menuItem.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, homeFragment).commit()
                activeFragment = homeFragment
                true
            }
            R.id.nav_cart -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, cartFragment).commit()
                activeFragment = cartFragment
                true
            }
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, profileFragment).commit()
                activeFragment = profileFragment
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
