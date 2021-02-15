package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.adapters.ContactSelectionRecyclerAdapter
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.data.viewmodel.IMCRelationsViewModel
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel

class UpdateSetContactsFragment : Fragment() {

    // Room DB
    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var mImgMsgViewModel: ImgMsgViewModel
    private lateinit var mIMCRelationsViewModel: IMCRelationsViewModel

    private lateinit var navController: NavController
    private lateinit var adapter: ContactSelectionRecyclerAdapter

    // Nav (args passed from previous frag)
    private val args by navArgs<UpdateSetContactsFragmentArgs>()

    // Vars for mutliple selection
    private val selectedItems = arrayListOf<Contact>()
    private var counter = 0
    private lateinit var toolbarHeaderTxt: TextView
    private lateinit var saveBtn: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_set_contacts, container, false)

        // Recyclerview setup
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        adapter = ContactSelectionRecyclerAdapter(mContactViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}