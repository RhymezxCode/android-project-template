package com.example.modularapp.core.data

import com.example.modularapp.core.model.Greeting
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultGreetingRepositoryTest {
    private val dataSource = FakeGreetingLocalDataSource()
    private val repository = DefaultGreetingRepository(dataSource)

    @Test
    fun `observe greeting delegates to local data source`() =
        runTest {
            assertThat(repository.observeGreeting().first().message).isEqualTo("Initial")
        }

    @Test
    fun `refresh greeting updates observed value`() =
        runTest {
            repository.refreshGreeting()

            assertThat(repository.observeGreeting().first().message).isEqualTo("Refreshed")
            assertThat(dataSource.refreshCount).isEqualTo(1)
        }
}

private class FakeGreetingLocalDataSource : GreetingLocalDataSource {
    private val greeting = MutableStateFlow(Greeting("Initial"))
    var refreshCount = 0
        private set

    override fun observeGreeting(): Flow<Greeting> = greeting

    override suspend fun refreshGreeting() {
        refreshCount += 1
        greeting.value = Greeting("Refreshed")
    }
}
