package com.company.rentafield.presentation.screens.profile

import androidx.compose.runtime.Stable
import com.company.rentafield.domain.model.LocalUser
import java.io.File

@Stable
data class ProfileUiState(
    val user: LocalUser = LocalUser(),
    val profileImage: File? = null,
    val oldProfileImage: String? = null,
    val feedback: String = "",
    val feedbackCategory: FeedbackCategory = FeedbackCategory.Suggestion,
)

@Stable
enum class FeedbackCategory {
    Suggestion,
    Issue,
    Complaint,
    Other
}