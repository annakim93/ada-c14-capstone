package com.example.adacapstone.Utils

import java.io.File

class FileSearch {
    companion object {
        // Search directory and return list of all dirs it contains
        fun getDirectoryPaths(directory: String?): ArrayList<String?> {
            val pathArray = arrayListOf<String?>()
            val file = File(directory)
            val listFiles = file.listFiles()

            if (listFiles != null) {
                for (i in listFiles) {
                    if (i.isDirectory) {
                        pathArray.add(i.absolutePath)
                    }
                }
            }
            return pathArray
        }

        // Search dir and return all files it contains
        fun getFilePaths(directory: String?): ArrayList<String> {
            val pathArray = arrayListOf<String>()
            val file = File(directory)
            val listFiles = file.listFiles()

            if (listFiles != null) {
                for (i in listFiles) {
                    if (i.isFile) {
                        pathArray.add(i.absolutePath)
                    }
                }
            }

            return pathArray
        }
    }
}