package com.example.adacapstone.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.InputCheck
import com.example.adacapstone.utils.Permissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment(), InputCheck {

    // Camera and gallery
    private lateinit var selectedImg: ImageView
    lateinit var selectedImgPath: String
    lateinit var selectedImgUri: Uri

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    // Nav (args passed from previous frag)
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        container?.removeAllViews()

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        selectedImg = view.findViewById(R.id.selected_update_img)
        selectedImgPath = args.currentImgMsg.imageFilePath
        selectedImg.setImageBitmap(BitmapFactory.decodeFile(selectedImgPath))

        if (selectedImgPath.contains("CAMERA")) {
            selectedImg.rotation = 90F
        }

        view.findViewById<EditText>(R.id.update_alert_text).setText(args.currentImgMsg.msg)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Model
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)

        // Click listener on close btn to navigate back to home fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_update_img_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_updateFragment_to_manageGrid)
        }

        // Click listener for update / save changes button
        val saveBtn: ImageView = view.findViewById(R.id.save_btn)

        saveBtn.setOnClickListener {
            val updatedMsg = view.findViewById<EditText>(R.id.update_alert_text).text.toString()
            val updatedImg = view.findViewById<ImageView>(R.id.selected_update_img)

            if (imgMsgInputCheck(updatedMsg, updatedImg)) {
                val updatedImgMsg = ImageMessage(args.currentImgMsg.imgMsgId, updatedMsg, selectedImgPath) // Create imgMsg object
                mImgMsgViewModel.updateImgMsg(updatedImgMsg)
                navController.navigate(R.id.action_updateFragment_to_manageGrid)
                Toast.makeText(requireContext(), "Successfully updated.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_SHORT).show()
            }
        }

        // Click listener for gallery selection
        val galleryBtn: Button = view.findViewById(R.id.update_gallery_btn)
        galleryBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, Permissions.GALLERY_REQUEST_CODE)
        }

        // Click listener for camera selection
        val cameraBtn: Button = view.findViewById(R.id.update_camera_btn)
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imgFile: File = createImageFile("camera")

            imgFile.also {
                selectedImgUri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImgUri)
                startActivityForResult(cameraIntent, Permissions.CAMERA_REQUEST_CODE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Permissions.CAMERA_REQUEST_CODE) {
            val bmp = BitmapFactory.decodeFile(selectedImgPath)
            selectedImg.setImageBitmap(bmp)

            val imgRotation = getCameraPhotoOrientation(requireContext(), selectedImgUri, selectedImgPath)
            selectedImg.rotation = imgRotation.toFloat()
        } else if (requestCode == Permissions.GALLERY_REQUEST_CODE) {
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
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var prefix: String

        prefix = if (mode == "camera") {
            "CAMERA_JPEG_${timeStamp}_"
        } else {
            "GALLERY_JPEG_${timeStamp}_"
        }

        return File.createTempFile(
                prefix,
                ".jpg",
                storageDir
        ).apply {
            selectedImgPath = absolutePath
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

}