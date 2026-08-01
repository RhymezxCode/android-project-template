package com.example.modularapp.core.domain

import javax.inject.Inject

class RefreshGreetingUseCase
    @Inject
    constructor(
        private val repository: GreetingRepository,
    ) {
        suspend operator fun invoke() = repository.refreshGreeting()
    }
