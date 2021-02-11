package com.example.adacapstone.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adacapstone.R
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

}