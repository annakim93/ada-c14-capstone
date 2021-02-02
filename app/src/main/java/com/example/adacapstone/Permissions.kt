package com.example.adacapstone
import android.Manifest

class Permissions {
    companion object {
        val PERMISSIONS: Array<String> = arrayOf(
          Manifest.permission.CAMERA,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val CAMERA_PERMISSION: Array<String> = arrayOf(Manifest.permission.CAMERA)

        val WRITE_STORAGE_PERMISSION: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val READ_STORAGE_PERMISSION: Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}