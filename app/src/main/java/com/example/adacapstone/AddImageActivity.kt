package com.example.adacapstone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.google.android.material.tabs.TabLayout

class AddImageActivity : AppCompatActivity() {
    private val CAMERA_TAB_NUM = 1
    private val VERIFY_PERMISSIONS_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        if (checkPermissions(Permissions.PERMISSIONS)) {

        } else {
            verifyPermissions(Permissions.PERMISSIONS)
        }

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