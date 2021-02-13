package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.adapters.ContactSelectionRecyclerAdapter
import com.example.adacapstone.adapters.ContactsRecyclerAdapter
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.data.viewmodel.IMCRelationsViewModel
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel

class SetContactsForImgMsgFragment : Fragment() {

    // Room DB
    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var mImgMsgViewModel: ImgMsgViewModel
    private lateinit var mIMCRelationsViewModel: IMCRelationsViewModel

    private lateinit var navController: NavController
    private val adapter = ContactSelectionRecyclerAdapter(this)

    // Nav (args passed from previous frag)
    private val args by navArgs<SetContactsForImgMsgFragmentArgs>()

    // Vars for mutliple selection
    private val selectedItems = arrayListOf<Contact>()
    private var counter = 0
    private lateinit var toolbarHeaderTxt: TextView
    private lateinit var saveBtn: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_select_contacts, container, false)

        // Recyclerview setup
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Contact data
        mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        mContactViewModel.readAllData.observe(viewLifecycleOwner, Observer { contact ->
            adapter.setData(contact)
        })

        return view

    }

    fun startSelection(contact: Contact) {
        selectedItems.add(contact)
        counter++
        updateToolbarHeader(counter)
        saveBtn.visibility = View.VISIBLE
    }

    fun manageSelection(contact: Contact) {
        if (selectedItems.contains(contact)) {
            selectedItems.remove(contact)
            counter--
            updateToolbarHeader(counter)
        } else {
            selectedItems.add(contact)
            counter++
            updateToolbarHeader(counter)
        }
    }

    private fun updateToolbarHeader(counter: Int) {
        if (counter == 0) {
            toolbarHeaderTxt.text = "Select Contacts"
            saveBtn.visibility = View.GONE
        } else if (counter == 1) {
            toolbarHeaderTxt.text = "1 contact selected"
        } else {
            toolbarHeaderTxt.text = counter.toString() + " contacts selected"
        }
    }

}