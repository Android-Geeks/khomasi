package com.company.rentafield.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun navigateToMaps(context: Context, lat: Double, long: Double, zoom: Float = 10.0f) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("geo:$lat,$long?z=$zoom&q=$lat,$long(Marker)")
    context.startActivity(intent)
}