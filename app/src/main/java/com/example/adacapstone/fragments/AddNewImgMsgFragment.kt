package com.example.adacapstone.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.Permissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddNewImgMsgFragment : Fragment() {

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    // Image vars
    private lateinit var selectedImg: ImageView
    lateinit var currentImgPath: String
    lateinit var currentImgUri: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        if (!checkPermissions(Permissions.IMG_PERMISSIONS)) {
            verifyPermissions(Permissions.IMG_PERMISSIONS)
        }

        val view = inflater.inflate(R.layout.fragment_add_new, container, false)
        selectedImg = view.findViewById(R.id.selected_img)

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
        val saveBtn: ImageView = view.findViewById(R.id.save_img_btn)

        saveBtn.setOnClickListener {
            val alertText: TextView = view.findViewById(R.id.alertText)
            val message = alertText.text.toString()

            if (inputCheck(message, selectedImg)) {
                val imgMsg = ImageMessage(0, message, currentImgPath) // Create imgMsg object
                mImgMsgViewModel.addImgMsg(imgMsg) // Add to db
                navController.navigate(R.id.action_addNewFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_SHORT).show()
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

    fun getCameraPhotoOrientation(context: Context, imageUri: Uri,
                                  imagePath: String): Int {
        var rotate = 0
        try {
            context.contentResolver.notifyChange(imageUri, null)
            val imageFile = File(imagePath)
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
            Log.i("RotateImage", "Exif orientation: $orientation")
            Log.i("RotateImage", "Rotate value: $rotate")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rotate
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
                Permissions.VERIFY_PERMISSIONS_REQUEST_CODE
        )
    }

}