package com.example.adacapstone.interfaces

import android.text.TextUtils
import android.widget.ImageView

interface InputCheck {

    fun imgMsgInputCheck(message: String, img: ImageView): Boolean {
        return !(TextUtils.isEmpty(message) || img.drawable == null)
    }

    fun contactInputCheck(name: String, num: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(num))
    }

}