package com.company.rentafield.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun parseErrorBody(errorBody: String?): String? {
    return try {
        val jsonElement = Json.parseToJsonElement(errorBody ?: "")
        val jsonObject = jsonElement.jsonObject
        return jsonObject["message"]?.jsonPrimitive?.content
    } catch (e: Exception) {
        null
    }
}