package com.example.modularapp.core.domain

import com.example.modularapp.core.model.Greeting
import kotlinx.coroutines.flow.Flow

interface GreetingRepository {
    fun observeGreeting(): Flow<Greeting>

    suspend fun refreshGreeting()
}
