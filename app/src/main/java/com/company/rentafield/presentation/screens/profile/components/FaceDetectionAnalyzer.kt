package com.company.rentafield.presentation.screens.profile.components

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import java.io.IOException

fun detectFace(
    context: Context,
    uri: Uri,
    onSuccessful: (Boolean) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val detector = FaceDetection.getClient()

    val image: InputImage = try {
        InputImage.fromFilePath(context, uri)
    } catch (e: IOException) {
        e.printStackTrace()
        return
    }

    detector.process(image)
        .addOnSuccessListener { faces ->
            onSuccessful(faces.isNotEmpty())
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}

