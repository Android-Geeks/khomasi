package com.company.khomasi.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

fun String.convertToBitmap(): Bitmap? {
    val byte = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byte, 0, byte.size)
}

fun Uri.toBase64String(context: Context): String {
    // Get the input stream of the image from the Uri
    val inputStream = context.contentResolver.openInputStream(this)

    // Decode the input stream into a Bitmap
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Convert the Bitmap into a ByteArray
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    // Encode the ByteArray into a Base64 String and return it
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}