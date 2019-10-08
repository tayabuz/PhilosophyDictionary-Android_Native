package com.philosophyDictionary

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        p0.isChecked = true
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawerLayout.closeDrawers()

        when (p0.itemId) {
            R.id.app_settings -> navController.navigate(R.id.settingsFragment)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val themeManager = ThemeManager(this)
        if (themeManager.isNightModeEnabled()) {
            themeManager.setAppTheme(R.style.AppTheme_Night)
        } else {
            themeManager.setAppTheme(R.style.AppTheme_Light)
        }
        super.onCreate(savedInstanceState)
        ContentManager.getInstance(this)?.terms
        setContentView(R.layout.activity_main)
        setupNavigation()
        viewModel =  ViewModelProvider(this).get(TermViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout)
    }


    override fun onBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)
    }

    companion object {
        lateinit var viewModel: TermViewModel
    }
}
