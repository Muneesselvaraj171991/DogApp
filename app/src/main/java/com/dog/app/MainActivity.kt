package com.dog.app

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment? ?: return
        val navController = navHostFragment.navController
        val actionBar = supportActionBar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.welcomeFragment) {
                actionBar?.hide()
            } else {
                if (destination.id == R.id.dogListFragment) {
                    actionBar?.title = getString(R.string.app_name)
                }

                if (destination.id == R.id.detailFragment) {
                    actionBar?.title = getString(R.string.detail)
                }
                actionBar?.show()
                actionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}