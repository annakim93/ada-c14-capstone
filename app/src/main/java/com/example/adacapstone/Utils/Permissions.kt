package com.example.adacapstone.Utils
import android.Manifest

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