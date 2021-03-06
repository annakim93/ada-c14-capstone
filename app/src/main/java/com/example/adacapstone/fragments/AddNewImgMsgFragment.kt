package com.example.adacapstone.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodels.ImgMsgViewModel
import com.example.adacapstone.interfaces.ImageHandling
import com.example.adacapstone.interfaces.InputCheck
import com.example.adacapstone.utils.Permissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddNewImgMsgFragment : Fragment(), InputCheck, ImageHandling {

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    // Image vars
    private lateinit var selectedImg: ImageView
    lateinit var currentImgPath: String
    lateinit var currentImgUri: Uri

    // Nav (args passed from previous frag)
    private val args by navArgs<AddNewImgMsgFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        if (!Permissions.checkPermissions(Permissions.IMG_PERMISSIONS, requireContext())) {
            Permissions.verifyPermissions(Permissions.IMG_PERMISSIONS, requireActivity())
        }

        if (!Permissions.checkPermissions(Permissions.LOCATION_PERMISSION, requireContext())) {
            Permissions.verifyPermissions(Permissions.LOCATION_PERMISSION, requireActivity())
        }

        val view = inflater.inflate(R.layout.fragment_add_new, container, false)
        selectedImg = view.findViewById(R.id.selected_img)

        if (!args.isProcessStart) {
            val imgMsgInProgress = args.currentImgMsg
            val selectedImgPath = imgMsgInProgress?.imageFilePath
            if (selectedImgPath != null) {
                currentImgPath = selectedImgPath
            }
            selectedImg.setImageBitmap(BitmapFactory.decodeFile(selectedImgPath))

            if (selectedImgPath?.contains("CAMERA") == true) {
                selectedImg.rotation = 90F
            }

            val alertText: TextView = view.findViewById(R.id.alertText)
            if (imgMsgInProgress != null) {
                alertText.text = imgMsgInProgress.msg
            }
        }

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
            startActivityForResult(galleryIntent, Permissions.GALLERY_REQUEST_CODE)
        }

        // Click listener for camera selection
        val cameraBtn: Button = view.findViewById(R.id.camera_btn)
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Create the File where the img should go
            val imgFile: File = createImageFile("camera")

            // Continue only if the File was successfully created
            imgFile.also {
                currentImgUri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentImgUri)
                startActivityForResult(cameraIntent, Permissions.CAMERA_REQUEST_CODE)
            }

        }

        // Click listener for submit / save button
        val nextBtn: ImageView = view.findViewById(R.id.next_btn)

        nextBtn.setOnClickListener{
            val alertText: TextView = view.findViewById(R.id.alertText)
            val message = alertText.text.toString()

            if (imgMsgInputCheck(message, selectedImg)) {
                val imgMsg = ImageMessage(0, message, currentImgPath) // Create imgMsg object
                navController.navigate(
                        AddNewImgMsgFragmentDirections
                                .actionAddNewFragmentToSetContactsForImgMsgFragment(imgMsg)
                )
                Toast.makeText(requireContext(), "Please select contacts to send this message to.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    // Camera and gallery image handling
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Permissions.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val bmp = BitmapFactory.decodeFile(currentImgPath)
            selectedImg.setImageBitmap(bmp)

            val imgRotation = getCameraPhotoOrientation(requireContext(), currentImgUri, currentImgPath)
            selectedImg.rotation = imgRotation.toFloat()
        } else if (requestCode == Permissions.GALLERY_REQUEST_CODE  && resultCode == RESULT_OK) {
            val uri = data?.data
            selectedImg.setImageURI(uri)

            val imgFile: File = createImageFile("gallery")

            imgFile.also {
                val fos = FileOutputStream(imgFile)
                val bmp = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(mode: String): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var prefix: String

        prefix = if (mode == "camera") {
            "CAMERA_JPEG_${timeStamp}_"
        } else {
            "GALLERY_JPEG_${timeStamp}_"
        }

        return File.createTempFile(
                prefix, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentImgPath = absolutePath
        }
    }

}