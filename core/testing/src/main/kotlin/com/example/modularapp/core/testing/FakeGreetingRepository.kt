package com.example.modularapp.core.testing

import com.example.modularapp.core.domain.GreetingRepository
import com.example.modularapp.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGreetingRepository(
    initialGreeting: Greeting = Greeting("Ready"),
) : GreetingRepository {
    private val greeting = MutableStateFlow(initialGreeting)

    var refreshCount: Int = 0
        private set
    var refreshFailure: Throwable? = null

    override fun observeGreeting(): Flow<Greeting> = greeting

    override suspend fun refreshGreeting() {
        refreshCount += 1
        refreshFailure?.let { throw it }
    }

    fun emit(value: Greeting) {
        greeting.value = value
    }
}
