package com.example.adacapstone.utils
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class Permissions {
    companion object {

        val VERIFY_PERMISSIONS_REQUEST_CODE = 1
        val CAMERA_REQUEST_CODE = 5
        val GALLERY_REQUEST_CODE = 6
        val SEND_SMS_REQUEST_CODE = 7

        val IMG_PERMISSIONS: Array<String> = arrayOf(
          Manifest.permission.CAMERA,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val SMS_PERMISSION: Array<String> = arrayOf(Manifest.permission.SEND_SMS)

        val CAMERA_PERMISSION = Manifest.permission.CAMERA

        val WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

    }
}