package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.adapters.ContactSelectionRecyclerAdapter
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
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

        // Instantiate lateinit vars for multi-select
        toolbarHeaderTxt = view.findViewById(R.id.select_contacts_header)
        saveBtn = view.findViewById(R.id.save_btn)

        // Click listener on close btn to navigate back to home fragment
        navController = Navigation.findNavController(view)
        val backBtn: ImageView = view.findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            navController.navigate(
                    UpdateSetContactsFragmentDirections
                            .actionUpdateSetContactsFragment2ToUpdateFragment(args.currentImgMsg)
            )
        }

        // Click listener - add new contact
        val addNewBtn: RelativeLayout = view.findViewById(R.id.add_new_layout)

        addNewBtn.setOnClickListener {
            navController.navigate(
                    UpdateSetContactsFragmentDirections
                            .actionUpdateSetContactsFragment2ToAddNewContactFragment(false, args.currentImgMsg, true, args.currentContactList)
            )
        }
//
//        // Click listener for save btn
//        saveBtn.setOnClickListener {
//            mImgMsgViewModel.addImgMsg(args.currentImgMsg)
//
//            mImgMsgViewModel.latestImgMsgId.observe(viewLifecycleOwner, Observer { it ->
//                for (contact in selectedItems) {
//                    mIMCRelationsViewModel.addIMCCrossRef(ImgMsgContactCrossRef(it.toInt(), contact.contactId))
//                }
//
//                navController.navigate(R.id.action_setContactsForImgMsgFragment_to_homeFragment)
//                Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_SHORT).show()
//            })
//        }

    }

}