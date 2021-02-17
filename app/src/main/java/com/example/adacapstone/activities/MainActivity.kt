package com.example.adacapstone.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.adacapstone.R
import com.example.adacapstone.fragments.AddNewImgMsgFragmentDirections
import com.example.adacapstone.fragments.HomeFragmentDirections
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment


class MainActivity : AppCompatActivity() {

    // Constants
    val HOME_INDEX = 0
    private val PLACEHOLDER_INDEX = 1
    lateinit var navView: BottomNavigationView

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
        navView = findViewById(R.id.nav_view)
        navView.background = null
        navView.menu.getItem(PLACEHOLDER_INDEX).isEnabled = false
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // FAB
        val navController = findNavController(R.id.nav_host_fragment)
        val fab: FloatingActionButton = findViewById(R.id.add_img_fab)
        fab.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddNewFragment(true))
        }

        // Nav bottom menu visibility
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if (destination.id != R.id.homeFragment) {
                navView.visibility = View.GONE
                bottomAppBar.visibility = View.GONE
                fab.hide()
            } else {
                navView.visibility = View.VISIBLE
                bottomAppBar.visibility = View.VISIBLE
                fab.show()
            }
        }

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.nav_home -> {
                if (!navView.menu.getItem(HOME_INDEX).isChecked) {
                    navController.navigate(R.id.action_contactsFragment_to_homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_contacts -> {
                navController.navigate(R.id.action_homeFragment_to_contactsFragment)
                Toast.makeText(this, "Click on a contact to update or long-click to delete.", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}