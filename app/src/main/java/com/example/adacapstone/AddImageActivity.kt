package com.example.adacapstone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.tabs.TabLayout

class AddImageActivity : AppCompatActivity() {
    private val CAMERA_TAB_NUM = 1
    private val VERIFY_PERMISSIONS_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 5

    private lateinit var galleryGrid: GridView
    private lateinit var selectedImg: ImageView
    private lateinit var directorySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        if (checkPermissions(Permissions.PERMISSIONS)) {

        } else {
            verifyPermissions(Permissions.PERMISSIONS)
        }

        // CAMERA TAB
        val tabLayout: TabLayout = findViewById(R.id.bottom_tabs)
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabNum = tab?.position
                if (tabNum == CAMERA_TAB_NUM) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                }
            }

            override fun onTabUnselected (tab: TabLayout.Tab) {
            }

            override fun onTabReselected (tab: TabLayout.Tab) {
            }
        })

        // GALLERY TAB
        selectedImg = findViewById(R.id.selected_img)
        galleryGrid = findViewById(R.id.gallery_grid)
        directorySpinner = findViewById(R.id.new_img_spinner)

        val newImgCloseBtn: ImageView = findViewById(R.id.close_add_img_btn)
        newImgCloseBtn.setOnClickListener { this@AddImageActivity.finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE) {

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