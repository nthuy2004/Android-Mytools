package vn.nth.mytools.ui.activities

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import vn.nth.mytools.R
import vn.nth.mytools.ui.base.BaseActivity
import vn.nth.mytools.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val TAG = "NTH_MYTOOLS_MAINACTIVITY"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding  : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNav()
        //LoadingDialog(this).show()
    }

    private fun initNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home,
            R.id.nav_monitor,
            R.id.nav_about
        ).build()
        setSupportActionBar(binding.appbarLayout.toolbar)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.appbarLayout.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val bottomNavigationView = binding.bottomNav
        bottomNavigationView.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
        bottomNavigationView.setupWithNavController(navController)
    }
}