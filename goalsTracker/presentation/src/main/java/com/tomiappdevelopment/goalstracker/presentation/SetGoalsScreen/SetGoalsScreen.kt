package com.tomiappdevelopment.goalstracker.presentation.SetGoalsScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tomiappdevelopment.core.presentation.designsystem.MyActivityTrackerTheme
import com.tomiappdevelopment.core.presentation.designsystem.StartIcon
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerFloatingActionButton
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerScaffold
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerToolbar
import com.tomiappdevelopment.goalstracker.presentation.R
import com.tomiappdevelopment.goalstracker.presentation.core.components.GoalPickerCard
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.compose.koinViewModel


@Composable
fun SetGoalsScreenRoot(
    onBack: () -> Unit,
    onStartActivity:()->Unit,
    onLogoutClick:() -> Unit,
    viewModel: SetGoalsViewModel = koinViewModel(),
    ) {
    val state by viewModel.uiState.collectAsState()
    ActiveRunScreen(
        state = state,
        uiMessage = viewModel.uiMessage,
        onAction = { action ->
            when(action) {
                SetGoalsEvents.OnLogoutClick -> onLogoutClick()
                SetGoalsEvents.OnBackClick -> onBack()
                SetGoalsEvents.OnStartActivityClick->onStartActivity()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActiveRunScreen(
    state: SetGoalsState,
    uiMessage: Channel<String>,
    onAction: (SetGoalsEvents) -> Unit
) {
    var localMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiMessage) {
        uiMessage.consumeAsFlow().collect { msg ->
            localMessage = msg
            delay(1500)
            localMessage = null
            onAction(SetGoalsEvents.OnBackClick)   // navigate back if needed
        }
    }


    MyActivityTrackerScaffold(
        withGradient = false,
        topAppBar = {
            MyActivityTrackerToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.SetGoalsHeader),
                onBackClick = {
                    onAction(SetGoalsEvents.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            MyActivityTrackerFloatingActionButton(
                icon = StartIcon,
                onClick = {
                     onAction(SetGoalsEvents.OnStartActivityClick)
                },
                iconSize = 20.dp,
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            AnimatedVisibility(
                visible = localMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text = localMessage.toString(),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoalPickerCard(
                    title = stringResource(id = R.string.distanceKm),
                    value = state.distanceGoal,
                    onValueChange = { onAction(SetGoalsEvents.OnDistanceGoalChange(it)) }
                )

                GoalPickerCard(
                    title = stringResource(id = R.string.WorkoutsQuantity),
                    value = state.workoutGoal,
                    onValueChange = { onAction(SetGoalsEvents.OnWorkoutGoalChange(it)) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onAction(SetGoalsEvents.OnSaveClick) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(text = stringResource(id = R.string.updateTargets))
                    }
                }
            }
        }
    }
}
@Preview(
    name = "Set Goals Screen",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ActiveRunScreenPreview() {
    val previewState = SetGoalsState(
        distanceGoal = 10,
        workoutGoal = 4,
        isSaving = false
    )

    MyActivityTrackerTheme {
        ActiveRunScreen(
            state = previewState,
            onAction = {},
            uiMessage = Channel<String>()
        )
    }
}

