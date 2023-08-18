package com.example.testassignmentgitrepo.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.testassignmentgitrepo.R
import com.example.testassignmentgitrepo.databinding.ActivityLayoutMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _activityLayoutMainBinding: ActivityLayoutMainBinding? = null
    private val activityLayoutMainBinding get() = _activityLayoutMainBinding!!

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityLayoutMainBinding = ActivityLayoutMainBinding.inflate(layoutInflater)
        setContentView(activityLayoutMainBinding.root)

        setSupportActionBar(activityLayoutMainBinding.mainToolbar)

        /*
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        It does more than just finding the controller.
        It traverses, creates fragment, creates views and throw an exception.
        View binding just generates a binding class with all the views of your layout in it.
        It is not meant for finding the navigation controller of the app.
        Hence binding can't be used here.
         */
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
