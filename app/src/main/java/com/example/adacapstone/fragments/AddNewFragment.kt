package com.example.adacapstone.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.Permissions

class AddNewFragment : Fragment() {

    // Constants
    private val VERIFY_PERMISSIONS_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 5
    private val GALLERY_REQUEST_CODE = 6

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    private lateinit var selectedImg: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        if (!checkPermissions(Permissions.PERMISSIONS)) {
            verifyPermissions(Permissions.PERMISSIONS)
        }

        val view = inflater.inflate(R.layout.fragment_add_new, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Model
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)

        // Click listener on close btn to navigate back to home fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_add_img_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_addNewFragment_to_homeFragment)
        }

        // Click listener for gallery selection
        val galleryBtn: Button = view.findViewById(R.id.gallery_btn)
        galleryBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        // Click listener for camera selection
        val cameraBtn: Button = view.findViewById(R.id.camera_btn)
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }

        // Click listener for submit / save button
        val saveBtn: ImageView = view.findViewById(R.id.save_img_btn)

        saveBtn.setOnClickListener {
            val alertText: TextView = view.findViewById(R.id.alertText)
            val message = alertText.text.toString()

            val selectedImg: ImageView = view.findViewById(R.id.selected_img)
            val selectedBM = (selectedImg.drawable as BitmapDrawable).bitmap

            if (inputCheck(message, selectedImg)) {
                val imgMsg = ImageMessage(0, message, selectedBM) // Create imgMsg object
                mImgMsgViewModel.addImgMsg(imgMsg) // Add to db
                activity?.finish()
                Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE) {
            val bmp = data?.extras?.get("data") as Bitmap
            selectedImg.setImageBitmap(bmp)
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            selectedImg.setImageURI(data?.data)
        }
    }

    // Database input check
    private fun inputCheck(message: String, image: ImageView): Boolean {
        return !(TextUtils.isEmpty(message) || image.drawable == null)
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
                VERIFY_PERMISSIONS_REQUEST_CODE
        )
    }

}