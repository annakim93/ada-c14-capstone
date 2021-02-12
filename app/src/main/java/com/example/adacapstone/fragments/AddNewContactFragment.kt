package com.example.adacapstone.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ContactViewModel
import com.example.adacapstone.utils.Permissions

class AddNewContactFragment : Fragment() {

    // Room database
    private lateinit var mContactViewModel: ContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        if (!checkPermissions(Permissions.SMS_PERMISSION)) {
            verifyPermissions(Permissions.SMS_PERMISSION)
        }

        val view = inflater.inflate(R.layout.fragment_add_new_contact, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Model
        val mContactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Click listener on close btn to navigate back to contacts fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_add_contact_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_addNewContactFragment_to_contactsFragment)
        }

        // Click listener for submit / save button
        val saveBtn: ImageView = view.findViewById(R.id.save_contact_btn)

        saveBtn.setOnClickListener {
            val nameInput: TextView = view.findViewById(R.id.contact_name_input)
            val name = nameInput.text.toString()

            val numInput: TextView = view.findViewById(R.id.contact_num_input)
            val num = numInput.text.toString()

            if (inputCheck(name, num)) {
                val contact = Contact(0, name, num)
                mContactViewModel.addContact(contact)
                navController.navigate(R.id.action_addNewContactFragment_to_contactsFragment)
                Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Permissions handling
    fun checkPermissions(permissions: Array<String>): Boolean {
        for (i in permissions) {
            if (!checkSinglePermission(i)) return false
        }
        return true
    }

    fun checkSinglePermission(permission: String): Boolean {
        val permissionRequest = ActivityCompat.checkSelfPermission(
                requireContext(),
                permission
        )
        return permissionRequest == PackageManager.PERMISSION_GRANTED
    }

    fun verifyPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                Permissions.VERIFY_PERMISSIONS_REQUEST_CODE
        )
    }

    // Database input check
    private fun inputCheck(name: String, num: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(num))
    }
}