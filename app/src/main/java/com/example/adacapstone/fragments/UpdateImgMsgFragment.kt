package com.example.adacapstone.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    // Camera and gallery
    private val CAMERA_REQUEST_CODE = 5
    private val GALLERY_REQUEST_CODE = 6
    private lateinit var selectedImg: ImageView
    lateinit var selectedImgPath: String

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

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
//            val updatedBM = (updatedImg.drawable as BitmapDrawable).bitmap

            if (inputCheck(updatedMsg, updatedImg)) {
                val updatedImgMsg = ImageMessage(args.currentImgMsg.id, updatedMsg, selectedImgPath) // Create imgMsg object
                mImgMsgViewModel.updateImgMsg(updatedImgMsg)
                navController.navigate(R.id.action_updateFragment_to_manageGrid)
                Toast.makeText(requireContext(), "Successfully updated.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
            }
        }

        // Click listener for gallery selection
        val galleryBtn: Button = view.findViewById(R.id.update_gallery_btn)
        galleryBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        // Click listener for camera selection
        val cameraBtn: Button = view.findViewById(R.id.update_camera_btn)
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imgFile: File = createImageFile()

            imgFile.also {
                val imgURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgURI)
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE) {
//            val bmp = data?.extras?.get("data") as Bitmap
//            selectedImg.setImageBitmap(bmp)

            val bmp = BitmapFactory.decodeFile(selectedImgPath)
            selectedImg.setImageBitmap(bmp)
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            val uri = data?.data
            selectedImg.setImageURI(uri)

            val imgFile: File = createImageFile()

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
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            selectedImgPath = absolutePath
        }
    }

    private fun inputCheck(message: String, img: ImageView): Boolean {
        return !(TextUtils.isEmpty(message) || img.drawable == null)
    }

}