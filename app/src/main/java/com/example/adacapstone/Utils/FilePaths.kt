package com.example.adacapstone.Utils

import android.content.Context
import android.os.Environment

class FilePaths(context: Context) {
    var ROOT_DIR: String? = context.getExternalFilesDir(null)?.absolutePath
    var PICS_DIR: String? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
    var CAM_DIR = "$ROOT_DIR/DCIM/camera"
}