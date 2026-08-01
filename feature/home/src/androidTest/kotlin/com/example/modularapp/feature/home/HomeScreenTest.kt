package com.example.modularapp.feature.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.modularapp.core.designsystem.StarterTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun greeting_and_refresh_action_are_accessible() {
        var refreshClicked = false
        composeRule.setContent {
            StarterTheme {
                HomeScreen(
                    state = HomeUiState(isLoading = false, greeting = "Foundation ready"),
                    snackbarHostState = SnackbarHostState(),
                    onRefresh = { refreshClicked = true },
                )
            }
        }

        composeRule.onNodeWithText("Foundation ready").assertIsDisplayed()
        composeRule.onNodeWithText("Refresh foundation message").performClick()
        assertTrue(refreshClicked)
    }
}
