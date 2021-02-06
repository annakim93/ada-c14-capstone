package com.example.adacapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.adacapstone.fragments.ContactsFragment
import com.example.adacapstone.fragments.HomeFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment


class MainActivity : AppCompatActivity() {

    // Constants
    private val PLACEHOLDER_INDEX = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bottom app bar
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottom_app_bar)
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.5f))
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.5f))
            .build()

        // Navigation bottom menu
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.background = null
        navView.menu.getItem(PLACEHOLDER_INDEX).isEnabled = false
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // FAB
        val fab: FloatingActionButton = findViewById(R.id.add_img_fab)
        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddImageActivity::class.java))
        }

        // Nav bottom menu visibility
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if (destination.id != R.id.homeFragment && destination.id != R.id.contactsFragment) {
                navView.visibility = View.GONE
                fab.visibility = View.GONE
                bottomAppBar.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
                bottomAppBar.visibility = View.VISIBLE
            }
        }

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.action_contactsFragment_to_homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_contacts -> {
                navController.navigate(R.id.action_homeFragment_to_contactsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}