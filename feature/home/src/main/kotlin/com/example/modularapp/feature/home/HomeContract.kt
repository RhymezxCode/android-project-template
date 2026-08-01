package com.example.modularapp.feature.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val greeting: String? = null,
    val errorMessage: String? = null,
)

sealed interface HomeAction {
    data object Refresh : HomeAction
}

sealed interface HomeEffect {
    data class ShowMessage(
        val message: String,
    ) : HomeEffect
}
