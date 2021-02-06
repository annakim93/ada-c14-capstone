package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.ListImageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel
//    private val PLACEHOLDER_INDEX = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Recyclerview setup
        val adapter = ListImageAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ImgMsgModel
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer { imgMsg ->
            adapter.setData(imgMsg)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener to navigate to manage photo-msgs fragment
        val navController: NavController = Navigation.findNavController(view)
        val gridBtn: ImageView = view.findViewById(R.id.manage_grid_btn)

        gridBtn.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_manageGrid)
            Toast.makeText(activity, "Click on a photo to update or delete.", Toast.LENGTH_LONG).show()
        }

//        // Bottom nav listener
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
    }

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.nav_home -> {
//                setFragment(HomeFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_contacts -> {
//                setFragment(ContactsFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
//
//    private fun setFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
//    }

}
