package com.example.adacapstone

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.adacapstone.Utils.FilePaths
import com.example.adacapstone.Utils.FileSearch
import com.example.adacapstone.Utils.GridImageAdapter
import com.example.adacapstone.Utils.Permissions
import com.google.android.material.tabs.TabLayout

class AddImageActivity : AppCompatActivity() {
    // Constants
    private val VERIFY_PERMISSIONS_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 5
    private val GALLERY_REQUEST_CODE = 6

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

        val nextActivityBtn: ImageView = findViewById(R.id.cont_add_img_btn)
        nextActivityBtn.setOnClickListener {} // TO:DO --> NAV TO MESSAGE SAVE SCREEN
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImg: ImageView = findViewById(R.id.selected_img)

        if (requestCode == CAMERA_REQUEST_CODE) {
            var bmp = data?.extras?.get("data") as Bitmap
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
        val permissionRequest = ActivityCompat.checkSelfPermission(this@AddImageActivity, permission)
        return permissionRequest == PackageManager.PERMISSION_GRANTED
    }

    fun verifyPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this@AddImageActivity, permissions, VERIFY_PERMISSIONS_REQUEST_CODE)
    }
}