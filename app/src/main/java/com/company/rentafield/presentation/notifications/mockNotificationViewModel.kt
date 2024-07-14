package com.company.rentafield.presentation.notifications


import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.ai.AiLog
import com.company.rentafield.domain.model.ai.AiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockNotificationViewModel : ViewModel() {
    private val _notificationState: MutableStateFlow<DataState<AiResponse>> =
        MutableStateFlow(
            DataState.Success(
                AiResponse(
                    listOf(
                        AiLog(
                            true,
                            100,
                            "2024-07-11T10:00:00"
                        ),
                        AiLog(
                            false,
                            0,
                            "2024-07-12T10:00:00"
                        ),
                        AiLog(
                            false,
                            0,
                            "2024-07-17T05:00:00"
                        ),
                        AiLog(
                            true,
                            10,
                            "2024-07-12T22:00:00"
                        )
                    )
                )
            )
        )
    val notificationState: StateFlow<DataState<AiResponse>> = _notificationState
    val localUser: StateFlow<LocalUser> = MutableStateFlow(LocalUser())


}
