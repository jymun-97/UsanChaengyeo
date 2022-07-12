package com.usanchaengyeo.usanchaengyeo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usanchaengyeo.usanchaengyeo.databinding.ActivityMainBinding
import com.usanchaengyeo.usanchaengyeo.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainerView.id, HomeFragment())
            commit()
        }
    }
}