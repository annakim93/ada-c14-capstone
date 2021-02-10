package com.example.adacapstone

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.Permissions


class AddImageActivity : AppCompatActivity() {
    // Constants
    private val VERIFY_PERMISSIONS_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 5
    private val GALLERY_REQUEST_CODE = 6

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        if (!checkPermissions(Permissions.PERMISSIONS)) {
            verifyPermissions(Permissions.PERMISSIONS)
        }

        // Button click handling
        val cameraBtn: Button = findViewById(R.id.camera_btn)
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }

        val galleryBtn: Button = findViewById(R.id.gallery_btn)
        galleryBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        val newImgCloseBtn: ImageView = findViewById(R.id.close_add_img_btn)
        newImgCloseBtn.setOnClickListener { this@AddImageActivity.finish() }

        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)

        val nextActivityBtn: ImageView = findViewById(R.id.save_img_btn)
        nextActivityBtn.setOnClickListener {
          addEntryToDatabase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImg: ImageView = findViewById(R.id.selected_img)

        if (requestCode == CAMERA_REQUEST_CODE) {
            val bmp = data?.extras?.get("data") as Bitmap
            selectedImg.setImageBitmap(bmp)
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            selectedImg.setImageURI(data?.data)
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
            this@AddImageActivity,
            permission
        )
        return permissionRequest == PackageManager.PERMISSION_GRANTED
    }

    fun verifyPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(
            this@AddImageActivity,
            permissions,
            VERIFY_PERMISSIONS_REQUEST_CODE
        )
    }

    // Room database
    private fun addEntryToDatabase() {
        val alertText: TextView = findViewById(R.id.alertText)
        val message = alertText.text.toString()

        val selectedImg: ImageView = findViewById(R.id.selected_img)
        val selectedBM = (selectedImg.drawable as BitmapDrawable).bitmap

        if (inputCheck(message, selectedImg)) {
            val imgMsg = ImageMessage(0, message, selectedBM) // Create imgMsg object
            mImgMsgViewModel.addImgMsg(imgMsg) // Add to db
            this@AddImageActivity.finish()
            Toast.makeText(this, "Successfully saved.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(message: String, image: ImageView): Boolean {
        return !(TextUtils.isEmpty(message) || image.drawable == null)
    }
}