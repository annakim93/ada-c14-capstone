package com.example.adacapstone.utils
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class Permissions {
    companion object {
        val PERMISSIONS: Array<String> = arrayOf(
          Manifest.permission.CAMERA,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val CAMERA_PERMISSION = Manifest.permission.CAMERA

        val WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

    }
}