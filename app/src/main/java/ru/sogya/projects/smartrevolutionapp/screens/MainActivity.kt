package ru.sogya.projects.smartrevolutionapp.screens

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View.*
import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.messaging.FirebaseMessaging
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        setupNavigation()
        setupLatestBackPressed()
        FirebaseMessaging.getInstance().subscribeToTopic("info")
            .addOnCompleteListener { task ->
                Toast.makeText(
                    this,
                    "Subscribed! You will get all discount offers notifications",
                    Toast.LENGTH_SHORT
                ).show()
                if (!task.isSuccessful) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        binding.hambutgerButton.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
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

    private fun setupLatestBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                for (i in 0 until supportFragmentManager.backStackEntryCount) {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
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