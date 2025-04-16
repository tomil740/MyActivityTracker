package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tomiappdevelopment.core.presentation.designsystem.AnalyticsIcon
import com.tomiappdevelopment.core.presentation.designsystem.LogoIcon
import com.tomiappdevelopment.core.presentation.designsystem.LogoutIcon
import com.tomiappdevelopment.core.presentation.designsystem.RunIcon
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerFloatingActionButton
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerScaffold
import com.tomiappdevelopment.core.presentation.designsystem.components.MyActivityTrackerToolbar
import com.tomiappdevelopment.core.presentation.designsystem.components.util.DropDownItem
import com.tomiappdevelopment.goalstracker.presentation.core.components.ActivityListItem
import com.tomiappdevelopment.goalstracker.presentation.core.components.MotivationPlate
import com.tomiappdevelopment.goalstracker.presentation.core.components.WeekOverview
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenRoot(
    onStartRunClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    HomeScreen(
        state = state,
        onAction = { action ->
            when(action) {
                HomeScreenEvents.OnLogoutClick -> onLogoutClick()
                HomeScreenEvents.OnStartActivity -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onAction:(HomeScreenEvents)-> Unit,
    modifier: Modifier = Modifier
){
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    MyActivityTrackerScaffold(
        modifier= modifier.fillMaxSize(),
        topAppBar = {
            MyActivityTrackerToolbar(
                showBackButton = false,
                title = stringResource(id = com.tomiappdevelopment.core.presentation.designsystem.R.string.runique),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id =  com.tomiappdevelopment.core.presentation.designsystem.R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id =  com.tomiappdevelopment.core.presentation.designsystem.R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> {}
                        1 -> onAction(HomeScreenEvents.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            MyActivityTrackerFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(HomeScreenEvents.OnStartActivity)
                }
            )
        }
    ) { paddingVal->
        Column(modifier = modifier.padding(paddingVal)) {
            WeekOverview(
                workouts = Pair(state.weekState.quantity.toFloat(),state.weeklyTargets.quantity.toFloat()),
                distance = Pair(state.weekState.distance.toFloat(),state.weeklyTargets.distance.toFloat()),
                onSetGoalsClick = {}
            )

            MotivationPlate(
                message = state.statusSentence,
                grade = state.performanceScore,
                modifier = modifier
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "This Week’s Activities",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    IconButton(onClick = { /* TODO: Navigate to all activities screen */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward, // or your own icon
                            contentDescription = "See all activities",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.weekActivities,
                        key = { it.id }
                    ) {
                        ActivityListItem(
                            activityGoalsData = it,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }

        }
    }



}