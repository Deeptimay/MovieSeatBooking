package com.example.testassignmentgitrepo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testassignmentgitrepo.R
import com.example.testassignmentgitrepo.databinding.ActivityLayoutMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // create binding instance for the activity_main.xml
    private lateinit var binding: ActivityLayoutMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(binding.navHostFragment)
        navController.navigate(R.id.trendingRepoFragment)
    }
}
