package com.company.rentafield.utils


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

fun String.convertToBitmap(): Bitmap? {
    val byte = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byte, 0, byte.size)
}

fun Uri.createImageFromUri(context: Context): File {
    val file = File(context.filesDir, "profile_image.png")
    context.contentResolver.openInputStream(this)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return file
}

fun File.toBase64String(): String {
    val bytes = this.readBytes()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
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

@SuppressLint("SimpleDateFormat")
fun Context.createVideoFile(): File {
    // Create a video file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val videoFileName = "MP4_" + timeStamp + "_"

    // Check if external cache directory is available
    val cacheDir = externalCacheDir ?: throw IOException("External cache directory not available")

    // Try to create a temporary file
    return try {
        File.createTempFile(
            videoFileName, /* prefix */
            ".mp4", /* suffix */
            cacheDir      /* directory */
        )
    } catch (e: IOException) {
        // Log the exception
        Log.e("createVideoFile", "Could not create video file", e)
        throw e
    }
}

fun Modifier.gradientOverlay(alpha: Float): Modifier {
    return drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = alpha)
                    ), startY = 0f, endY = Float.POSITIVE_INFINITY
                )
            )
        }
    }
}