package com.example.adacapstone.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.MainActivity
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.utils.ContactsRecyclerAdapter

class ContactsFragment : Fragment() {

    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var navController: NavController
    private val adapter = ContactsRecyclerAdapter(this)

    // Action mode vars for mutliple selection
    private var isActionMode = false
    private val selectedItems = arrayListOf<Contact>()
    private var counter = 0
    private lateinit var toolbarHeaderTxt: TextView
    private lateinit var deleteBtn: ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        // Recyclerview setup
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

        // Instantiate lateinit vars for multi-select
        toolbarHeaderTxt = view.findViewById(R.id.add_new_contact_header)
        deleteBtn = view.findViewById(R.id.delete_btn)

        // Click listener on close btn to navigate back to home fragment
        navController = Navigation.findNavController(view)
        val backBtn: ImageView = view.findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_homeFragment)
            val activity: MainActivity = activity as MainActivity
            activity.navView.menu.getItem(activity.HOME_INDEX).isChecked = true
        }

        // Click listener - add new contact
        val addNewBtn: RelativeLayout = view.findViewById(R.id.add_new_layout)

        addNewBtn.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_addNewContactFragment)
        }

        // Click listener for delete btn
        deleteBtn.setOnClickListener {
            deleteImgMsg()
        }
    }

    private fun deleteImgMsg() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            for (contact in selectedItems) {
                mContactViewModel.deleteContact(contact)
            }

            mContactViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.setData(it)
                }
            })

            isActionMode = false
            selectedItems.clear()
            adapter.selectedItems.clear()
            adapter.multiSelect = false
            counter = 0
            updateToolbarHeader(counter)
            Toast.makeText(context, "Successfully removed.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        if (selectedItems.size == 1) {
            builder.setMessage("Are you sure you want to delete this contact?")
        } else {
            builder.setMessage("Are you sure you want to delete these contacts?")
        }
        builder.create().show()
    }

    fun startSelection(contact: Contact) {
        if (!isActionMode) {
            isActionMode = true
            selectedItems.add(contact)
            counter++
            updateToolbarHeader(counter)
            deleteBtn.visibility = View.VISIBLE
        }
    }

    fun manageSelection(contact: Contact) {
        if (selectedItems.contains(contact)) {
            selectedItems.remove(contact)
            counter--
            updateToolbarHeader(counter)

            if (selectedItems.size == 0) {
                isActionMode = false
            }

        } else {
            selectedItems.add(contact)
            counter++
            updateToolbarHeader(counter)
        }
    }

    private fun updateToolbarHeader(counter: Int) {
        if (counter == 0) {
            toolbarHeaderTxt.text = "Contacts"
            deleteBtn.visibility = View.GONE
        } else if (counter == 1) {
            toolbarHeaderTxt.text = "1 contact selected"
        } else {
            toolbarHeaderTxt.text = counter.toString() + " contacts selected"
        }
    }

}