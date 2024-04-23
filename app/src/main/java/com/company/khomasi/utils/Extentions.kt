package com.company.khomasi.utils


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

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

fun Uri.toByteArray(context: Context): ByteArray? {
    // Get the input stream of the image from the Uri
    val inputStream = context.contentResolver.openInputStream(this) ?: return null

    // Create a ByteArrayOutputStream
    val byteBuffer = ByteArrayOutputStream()

    // Create a buffer
    val buffer = ByteArray(1024)
    var len: Int

    // Read the bytes from the InputStream into the ByteArrayOutputStream
    while (inputStream.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }

    // Close the InputStream
    inputStream.close()

    // Return the bytes from the ByteArrayOutputStream
    return byteBuffer.toByteArray()
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}