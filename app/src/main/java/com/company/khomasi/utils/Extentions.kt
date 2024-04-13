package com.company.khomasi.utils


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun String.convertToBitmap(): Bitmap? {
    val byte = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byte, 0, byte.size)
}

fun Bitmap.convertToBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}