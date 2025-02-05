package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import baltiapps.training.lifecycleplayground.R
import baltiapps.training.lifecycleplayground.ui.components.getTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ActivityLifecycleMainComposable(
    viewModel: ActivityLifecycleViewModel,
    showDialog: () -> Unit,
    onBack: () -> Unit,
    launchChildComposable: () -> Unit,
    launchChildActivity: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            viewModel.pushToComposeHistory(event.name)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var shouldShowComposeLifecycleHistory by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.safeContent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (shouldShowComposeLifecycleHistory) {
                            stringResource(R.string.compose_lifecycle_history)
                        } else stringResource(R.string.activity_lifecycle_history),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.go_back),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        TwoPane(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            first = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val setToShow = if (shouldShowComposeLifecycleHistory) {
                            viewModel.composeLifecycleHistory
                        } else viewModel.activityStateHistory
                        setToShow.toList().sorted().run {
                            if (isEmpty()) {
                                item {
                                    Text(stringResource(R.string.empty))
                                }
                            } else {
                                itemsIndexed(this) { index, item ->
                                    Text("$index - $item")
                                }
                            }
                        }
                    }
                }
            },
            second = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(stringResource(R.string.compose_lifecycle_history))
                        Switch(
                            checked = shouldShowComposeLifecycleHistory,
                            onCheckedChange = {
                                shouldShowComposeLifecycleHistory = it
                            }
                        )
                    }
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterHorizontally,
                        ),
                    ) {
                        OutlinedButton(
                            onClick = showDialog
                        ) {
                            Text(stringResource(R.string.show_dialog))
                        }
                        OutlinedButton(
                            onClick = launchChildActivity
                        ) {
                            Text(stringResource(R.string.child_activity))
                        }
                        OutlinedButton(
                            onClick = launchChildComposable
                        ) {
                            Text(stringResource(R.string.child_composable))
                        }
                        FilledIconButton(
                            onClick = {
                                viewModel.clearHistory()
                            },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(R.string.clear_history)
                            )
                        }
                    }
                }
            },
            strategy = getTwoPaneStrategy(),
            displayFeatures = listOf()
        )
    }
}