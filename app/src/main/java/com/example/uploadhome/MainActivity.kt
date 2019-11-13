package com.example.uploadhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val navController = findNavController(R.id.nav_host_fragment)
        val viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        viewModel.navigationCommands?.observe(this, Observer {
            when (it) {
                is NavigationCenter.To -> {
                    when (it.destination) {
                        R.id.photoFragment -> {
                            navController.navigate(R.id.photoFragment)
                        }

                    }
                }
            }
        })
    }

}
