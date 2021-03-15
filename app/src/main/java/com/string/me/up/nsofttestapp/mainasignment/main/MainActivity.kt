package com.string.me.up.nsofttestapp.mainasignment.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.string.me.up.nsofttestapp.R
import com.string.me.up.nsofttestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mCastContext: CastContext? = null
    private var mediaRouteMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val navController = Navigation.findNavController(this, R.id.main_nav_host)
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        mCastContext = CastContext.getSharedInstance(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.browse, menu)
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return true
    }
}