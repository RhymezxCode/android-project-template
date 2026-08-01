package com.example.modularapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modularapp.core.domain.ObserveGreetingUseCase
import com.example.modularapp.core.domain.RefreshGreetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val observeGreeting: ObserveGreetingUseCase,
        private val refreshGreeting: RefreshGreetingUseCase,
    ) : ViewModel() {
        private val mutableState = MutableStateFlow(HomeUiState())
        val state: StateFlow<HomeUiState> = mutableState

        private val effectChannel = Channel<HomeEffect>(capacity = Channel.BUFFERED)
        val effects = effectChannel.receiveAsFlow()

        init {
            observeGreetingState()
            refresh()
        }

        fun onAction(action: HomeAction) {
            when (action) {
                HomeAction.Refresh -> refresh()
            }
        }

        private fun observeGreetingState() {
            viewModelScope.launch {
                observeGreeting()
                    .catch { error -> handleError(error) }
                    .collect { greeting ->
                        mutableState.update {
                            it.copy(
                                isLoading = false,
                                greeting = greeting.message,
                                errorMessage = null,
                            )
                        }
                    }
            }
        }

        private fun refresh() {
            mutableState.update { it.copy(isLoading = true, errorMessage = null) }
            viewModelScope.launch {
                runCatching { refreshGreeting() }
                    .onFailure(::handleError)
                mutableState.update { it.copy(isLoading = false) }
            }
        }

        private fun handleError(error: Throwable) {
            val message = error.message?.takeIf(String::isNotBlank) ?: "Something went wrong"
            mutableState.update { it.copy(isLoading = false, errorMessage = message) }
            effectChannel.trySend(HomeEffect.ShowMessage(message))
        }
    }
