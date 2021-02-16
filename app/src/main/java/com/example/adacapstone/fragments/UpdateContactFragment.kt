package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.viewmodels.ContactViewModel
import com.example.adacapstone.interfaces.InputCheck

class UpdateContactFragment : Fragment(), InputCheck {

    // Room DB
    private lateinit var mContactViewModel: ContactViewModel

    // Nav (args passed from previous frag)
    private val args by navArgs<UpdateContactFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.removeAllViews()

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_contact, container, false)

        // Set text values with data from Contact
        val name = view.findViewById<EditText>(R.id.contact_name_input)
        name.setText(args.contact.name)

        val num = view.findViewById<EditText>(R.id.contact_num_input)
        num.setText(args.contact.phoneNumber)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Click listener on close btn to navigate back to home fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_update_contact_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_updateContactFragment_to_contactsFragment)
        }

        // Click listener for update / save changes button
        val saveBtn: ImageView = view.findViewById(R.id.save_contact_btn)

        saveBtn.setOnClickListener {
            val updatedName = view.findViewById<EditText>(R.id.contact_name_input).text.toString()
            val updatedNum = view.findViewById<EditText>(R.id.contact_num_input).text.toString()

            if (contactInputCheck(updatedName, updatedNum)) {
                val updatedContact = Contact(args.contact.contactId, updatedName, updatedNum)
                mContactViewModel.updateContact(updatedContact)
                navController.navigate(R.id.action_updateContactFragment_to_contactsFragment)
                Toast.makeText(requireContext(), "Successfully updated.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}