package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.MainActivity
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.utils.ContactsRecyclerAdapter

class ContactsFragment : Fragment() {

    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        // Recyclerview setup
        val adapter = ContactsRecyclerAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ImgMsgModel
        mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        mContactViewModel.readAllData.observe(viewLifecycleOwner, Observer { contact ->
            adapter.setData(contact)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener on close btn to navigate back to home fragment
        navController = Navigation.findNavController(view)
        val backBtn: ImageView = view.findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_homeFragment)
            val activity: MainActivity = activity as MainActivity
            activity.navView.menu.getItem(activity.HOME_INDEX).isChecked = true
        }
    }

}