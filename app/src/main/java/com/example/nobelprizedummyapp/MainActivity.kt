package com.example.nobelprizedummyapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.nobelprizedummyapp.databinding.ActivityMainBinding
import com.example.nobelprizedummyapp.view.NobelPrizesFragment
import com.example.nobelprizedummyapp.view.SpecialLaureatesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragment by lazy { NobelPrizesFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, NobelPrizesFragment.TAG)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            getString(R.string.show_special_laureates_text) -> {
                if (supportFragmentManager.findFragmentByTag(SpecialLaureatesFragment.TAG) == null) {
                    fragment.showSpecialLaureates()
                }
            }
            getString(R.string.exit_text) -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }
}