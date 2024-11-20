package com.company.rentafield.presentation.screens.profile

import java.io.File

data class ProfileUiState(
    val user: com.company.rentafield.data.models.LocalUser = com.company.rentafield.data.models.LocalUser(),
    val profileImage: File? = null,
    val oldProfileImage: String? = null,
    val feedback: String = "",
    val feedbackCategory: FeedbackCategory = FeedbackCategory.Suggestion,
)

enum class FeedbackCategory {
    Suggestion,
    Issue,
    Complaint,
    Other
}