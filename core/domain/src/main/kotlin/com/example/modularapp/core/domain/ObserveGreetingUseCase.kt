package com.example.modularapp.core.domain

import com.example.modularapp.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGreetingUseCase
    @Inject
    constructor(
        private val repository: GreetingRepository,
    ) {
        operator fun invoke(): Flow<Greeting> = repository.observeGreeting()
    }
