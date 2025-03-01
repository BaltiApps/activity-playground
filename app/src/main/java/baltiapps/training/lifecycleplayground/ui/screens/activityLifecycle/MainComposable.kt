package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.runtime.LaunchedEffect
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
fun MainComposable(
    viewModel: ActivityLifecycleViewModel,
    showDialog: () -> Unit,
    onBack: () -> Unit,
    launchChildComposable: () -> Unit,
    launchChildActivity: () -> Unit,
    launchDialogActivity: () -> Unit,
    launchDialogComposable: () -> Unit,
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
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val lazyColumnState = rememberLazyListState()

                        val setToShow = if (shouldShowComposeLifecycleHistory) {
                            viewModel.composeLifecycleHistory
                        } else viewModel.activityStateHistory

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            state = lazyColumnState,
                        ) {
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
                        LaunchedEffect(setToShow) {
                            lazyColumnState.scrollToItem(setToShow.size)
                        }
                    }
                }
            },
            second = {
                val scrollState = rememberScrollState()
                val buttonPadding = PaddingValues(horizontal = 8.dp)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            contentPadding = buttonPadding,
                            onClick = showDialog
                        ) {
                            Text(stringResource(R.string.show_dialog))
                        }
                        OutlinedButton(
                            contentPadding = buttonPadding,
                            onClick = launchChildActivity
                        ) {
                            Text(stringResource(R.string.child_activity))
                        }
                        OutlinedButton(
                            contentPadding = buttonPadding,
                            onClick = launchChildComposable
                        ) {
                            Text(stringResource(R.string.child_composable))
                        }
                        FilledTonalButton(
                            contentPadding = buttonPadding,
                            onClick = launchDialogActivity
                        ) {
                            Text(stringResource(R.string.dialog_activity))
                        }
                        FilledTonalButton(
                            contentPadding = buttonPadding,
                            onClick = launchDialogComposable
                        ) {
                            Text(stringResource(R.string.dialog_composable))
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