package com.example.modularapp.core.data

import com.example.modularapp.core.domain.GreetingRepository
import com.example.modularapp.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultGreetingRepository
    @Inject
    constructor(
        private val localDataSource: GreetingLocalDataSource,
    ) : GreetingRepository {
        override fun observeGreeting(): Flow<Greeting> = localDataSource.observeGreeting()

        override suspend fun refreshGreeting() = localDataSource.refreshGreeting()
    }
