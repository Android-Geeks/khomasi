package com.company.khomasi.presentation.profile

import com.company.khomasi.domain.model.LocalUser
import java.io.File

data class ProfileUiState(
    val user: LocalUser = LocalUser(),
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