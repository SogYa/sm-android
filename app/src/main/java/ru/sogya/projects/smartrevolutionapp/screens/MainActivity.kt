package ru.sogya.projects.smartrevolutionapp.screens

import android.os.Bundle
import android.view.View.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.sogya.data.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.ActivityMainBinding
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var baseUrl: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SplashScreen)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
    }


    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navView.setupWithNavController(navController)
        //Передача URL в текст заголовка меню
        baseUrl = binding.navView.getHeaderView(0).findViewById(R.id.baseUrl)
        baseUrl.text =
            SPControl.getIstance().getStringPrefs(Constants.URI)
        baseUrl.isSelected = true

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}