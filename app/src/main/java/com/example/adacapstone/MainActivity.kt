package com.example.adacapstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.adacapstone.Fragments.ContactsFragment
import com.example.adacapstone.Fragments.HomeFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment


class MainActivity : AppCompatActivity() {

    // Constants
    private val PLACEHOLDER_INDEX = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bottom App Bar
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottom_app_bar)
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.5f))
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.5f))
            .build()

        // Navigation Menu
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.background = null
        navView.menu.getItem(PLACEHOLDER_INDEX).isEnabled = false
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setFragment(HomeFragment())

//        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                setFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_add -> {
//                item.isChecked = false
//                startActivity(Intent(this@MainActivity, AddImageActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.nav_contacts -> {
                setFragment(ContactsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}