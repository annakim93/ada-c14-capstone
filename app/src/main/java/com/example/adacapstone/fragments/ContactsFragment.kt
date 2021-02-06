package com.example.adacapstone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.adacapstone.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContactsFragment : Fragment() {

    private val PLACEHOLDER_INDEX = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Bottom nav listener
//        val navController: NavController = Navigation.findNavController(view)
//        val bottomNav = view.findViewById<BottomNavigationView>(R.id.nav_view)
//        bottomNav.background = null
//        bottomNav.menu.getItem(PLACEHOLDER_INDEX).isEnabled = false
//        bottomNav.setOnNavigationItemSelectedListener { it ->
//            when (it.itemId) {
//                R.id.nav_home -> {
//                    navController.navigate(R.id.action_contactsFragment_to_homeFragment)
//                    true
//                }
//                R.id.nav_contacts -> {
//                    navController.navigate(R.id.action_homeFragment_to_contactsFragment)
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}