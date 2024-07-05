package com.company.rentafield.presentation.ai

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.use_case.ai.AiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val aiUseCases: AiUseCases
) : ViewModel()