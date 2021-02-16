package com.example.adacapstone.utils
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext

class Permissions {
    companion object {

        val VERIFY_PERMISSIONS_REQUEST_CODE = 1
        val CAMERA_REQUEST_CODE = 5
        val GALLERY_REQUEST_CODE = 6
        val SEND_SMS_REQUEST_CODE = 7
        val LOCATION_REQUEST_CODE = 8

        val IMG_PERMISSIONS: Array<String> = arrayOf(
          Manifest.permission.CAMERA,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val SMS_PERMISSION: Array<String> = arrayOf(Manifest.permission.SEND_SMS)

        val LOCATION_PERMISSION: Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        // Permissions handling
        fun checkPermissions(permissions: Array<String>, context: Context): Boolean {
            for (i in permissions) {
                if (!checkSinglePermission(i, context)) return false
            }
            return true
        }

        fun checkSinglePermission(permission: String, context: Context): Boolean {
            val permissionRequest = ActivityCompat.checkSelfPermission(
                    context,
                    permission
            )
            return permissionRequest == PackageManager.PERMISSION_GRANTED
        }

        fun verifyPermissions(permissions: Array<String>, activity: Activity) {
            ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    VERIFY_PERMISSIONS_REQUEST_CODE
            )
        }

    }
}