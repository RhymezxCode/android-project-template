package com.example.modularapp.core.data

import com.example.modularapp.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

internal interface GreetingLocalDataSource {
    fun observeGreeting(): Flow<Greeting>

    suspend fun refreshGreeting()
}

@Singleton
internal class InMemoryGreetingLocalDataSource
    @Inject
    constructor() : GreetingLocalDataSource {
        private val messages =
            listOf(
                "Your modular Android foundation is ready.",
                "Architecture, tests, and automation are working together.",
                "Build the next product feature with confidence.",
            )
        private var messageIndex = 0
        private val greeting = MutableStateFlow(Greeting(messages[messageIndex]))

        override fun observeGreeting(): Flow<Greeting> = greeting

        override suspend fun refreshGreeting() {
            messageIndex = (messageIndex + 1) % messages.size
            greeting.value = Greeting(messages[messageIndex])
        }
    }
