package com.example.adacapstone.Utils

import java.io.File

class FileSearch {
    fun getDirectoryPaths(directory: String): ArrayList<String> {
        val pathArray = arrayListOf<String>()
        val file = File(directory)
        val listFiles = file.listFiles()

        for (i in listFiles) {
            if (i.isDirectory()) {
                pathArray.add(i.absolutePath)
            }
        }
        return pathArray
    }
}