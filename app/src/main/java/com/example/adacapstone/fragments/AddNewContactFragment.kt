package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.interfaces.InputCheck
import com.example.adacapstone.utils.Permissions

class AddNewContactFragment : Fragment(), InputCheck {

    // Room database
    private lateinit var mContactViewModel: ContactViewModel

    private val args by navArgs<AddNewContactFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        if (!Permissions.checkPermissions(Permissions.SMS_PERMISSION, requireContext())) {
            Permissions.verifyPermissions(Permissions.SMS_PERMISSION, requireActivity())
        }

        return inflater.inflate(R.layout.fragment_add_new_contact, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Model
        mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Click listener on close btn to navigate back to contacts fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_add_contact_btn)

        if (args.newImgMsg) {
            closeBtn.setOnClickListener {
                navController.navigate(AddNewContactFragmentDirections.actionAddNewContactFragmentToSetContactsForImgMsgFragment(args.currentImgMsg!!))
            }
        } else {
            closeBtn.setOnClickListener {
                navController.navigate(R.id.action_addNewContactFragment_to_contactsFragment)
            }
        }

        // Click listener for submit / save button
        val saveBtn: ImageView = view.findViewById(R.id.save_contact_btn)

        saveBtn.setOnClickListener {
            val nameInput: TextView = view.findViewById(R.id.contact_name_input)
            val name = nameInput.text.toString()

            val numInput: TextView = view.findViewById(R.id.contact_num_input)
            val num = numInput.text.toString()

            if (contactInputCheck(name, num)) {
                val contact = Contact(0, name, num)
                mContactViewModel.addContact(contact)

                if (args.newImgMsg) {
                    navController.navigate(AddNewContactFragmentDirections.actionAddNewContactFragmentToSetContactsForImgMsgFragment(args.currentImgMsg!!))
                } else if (args.updateImgMsg) {
                    navController.navigate(AddNewContactFragmentDirections.actionAddNewContactFragmentToUpdateSetContactsFragment2(args.selectedContacts!!, args.currentImgMsg!!))
                } else {
                    navController.navigate(R.id.action_addNewContactFragment_to_contactsFragment)
                }

                Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
            }
        }
    }

}