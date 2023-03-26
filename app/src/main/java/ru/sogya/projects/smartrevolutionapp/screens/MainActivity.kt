package ru.sogya.projects.smartrevolutionapp.screens

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.ActivityMainBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.LogOutDialogFragment
import ru.sogya.projects.smartrevolutionapp.dialogs.NetworkConnectionDialog

class MainActivity : AppCompatActivity(), LogOutDialogFragment.DialogFragmentListener {
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var serverUri: TextView
    private lateinit var serverName: TextView
    private lateinit var vm: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SmartRevolutionApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        vm = ViewModelProvider(this)[MainVM::class.java]
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authFragment, R.id.lockFragment, R.id.firebaseAuthFragment, R.id.firebaseRegistrationFragment -> {
                    supportActionBar?.hide()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.bottomNav.visibility = GONE
                }
                R.id.firebaseAccountFragment, R.id.serversFragment -> {
                    supportActionBar?.hide()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.bottomNav.visibility = VISIBLE
                }
                else -> {
                    supportActionBar?.show()
                    binding.bottomNav.visibility = GONE
                }
            }
        }
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.groupFragment,
                R.id.settingsFragment,
                R.id.ticketListFragment,
                R.id.mapFragment
            ),
            binding.drawerLayout
        )
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navView.setupWithNavController(navController)
        //Передача URL в текст заголовка меню
        serverUri = binding.navView.getHeaderView(0).findViewById(R.id.baseUrl)
        serverName = binding.navView.getHeaderView(0).findViewById(R.id.serverName)
        serverUri.isSelected = true
    }

    fun getServerState() {
        vm.getServerState()
    }

    override fun onResume() {
        super.onResume()
        vm.getServerNameLiveData().observe(this) {
            serverName.text = it
        }
        vm.getServerUriLiveData().observe(this) {
            serverUri.text = it
        }
        vm.getNetworkStateLiveData().observe(this) {
            val dialogFragment = NetworkConnectionDialog()
            if (!it) {
                dialogFragment.show(supportFragmentManager, dialogFragment.tag)
            }
        }
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

    override fun positiveButtonClicked() {
        vm.logOut()
        navController.navigate(R.id.action_settingsFragment_to_serversFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}