package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import baltiapps.training.lifecycleplayground.R
import baltiapps.training.lifecycleplayground.ui.components.InfoBubble
import baltiapps.training.lifecycleplayground.ui.components.getTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StackCommonLayout(
    activityName: String,
    activityObjectId: String,
    activityDescriptions: List<String>,
    getAppTasksList: () -> List<Int>,
    getRunningTasksList: () -> List<Int>,
    getActivitiesCount: () -> Int,
    getBaseActivity: () -> String,
) {
    val activity = LocalActivity.current ?: return

    val activityList = activityList()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    var isCheckedFlagNewTask by rememberSaveable { mutableStateOf(false) }
    var isCheckedFlagMultipleTask by rememberSaveable { mutableStateOf(false) }
    var isCheckedFlagClearTop by rememberSaveable { mutableStateOf(false) }
    var isCheckedFlagSingleTop by rememberSaveable { mutableStateOf(false) }

    var shouldShowDialog by remember { mutableStateOf(false) }

    if (shouldShowDialog) {
        Alert {
            shouldShowDialog = false
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = {
                            shouldShowDialog = !shouldShowDialog
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.launch_info)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        TwoPane(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            first = {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = activityName,
                            style = MaterialTheme.typography.displayLarge,
                        )
                        Text(
                            text = "${stringResource(R.string.object_id)} - $activityObjectId",
                            style = MaterialTheme.typography.labelMedium,
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            activityDescriptions.forEach {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text("${stringResource(R.string.task_id)} - ${activity.taskId}")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("${stringResource(R.string.app_tasks)} - ${getAppTasksList()}")
                        InfoBubble(stringResource(R.string.tooltip_app_tasks))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("${stringResource(R.string.running_tasks)} - ${getRunningTasksList()}")
                        InfoBubble(stringResource(R.string.tooltip_running_tasks))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("${stringResource(R.string.activity_count)} - ${getActivitiesCount()}")
                        InfoBubble(stringResource(R.string.tooltip_activity_count))
                    }
                    Text("${stringResource(R.string.base_activity)} - ${getBaseActivity()}")
                }
            },
            second = {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                ) {
                    CheckBoxRow(
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = isCheckedFlagNewTask,
                        onChanged = { isCheckedFlagNewTask = it },
                        label = "FLAG - NEW_TASK",
                    )
                    CheckBoxRow(
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = isCheckedFlagMultipleTask,
                        onChanged = { isCheckedFlagMultipleTask = it },
                        label = "FLAG - MULTIPLE_TASK",
                    )
                    CheckBoxRow(
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = isCheckedFlagClearTop,
                        onChanged = { isCheckedFlagClearTop = it },
                        label = "FLAG - CLEAR_TOP",
                    )
                    CheckBoxRow(
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = isCheckedFlagSingleTop,
                        onChanged = { isCheckedFlagSingleTop = it },
                        label = "FLAG - SINGLE_TOP",
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LaunchDropdown(
                            modifier = Modifier.weight(1F),
                            selectedIndex = selectedIndex,
                            onItemSelected = { selectedIndex = it },
                            activityList = activityList,
                        )
                        FloatingActionButton(
                            onClick = {
                                val flagMap = mapOf(
                                    Pair(isCheckedFlagNewTask, Intent.FLAG_ACTIVITY_NEW_TASK),
                                    Pair(isCheckedFlagMultipleTask, Intent.FLAG_ACTIVITY_MULTIPLE_TASK),
                                    Pair(isCheckedFlagClearTop, Intent.FLAG_ACTIVITY_CLEAR_TOP),
                                    Pair(isCheckedFlagSingleTop, Intent.FLAG_ACTIVITY_SINGLE_TOP),
                                )

                                val flagsToApply: List<Int> = flagMap.filter { it.key }.map { it.value }

                                activityList[selectedIndex].copy(flags = flagsToApply).run {
                                    selectedIndex = 0

                                    isCheckedFlagNewTask = false
                                    isCheckedFlagMultipleTask = false
                                    isCheckedFlagClearTop = false
                                    isCheckedFlagSingleTop = false

                                    activity.launch()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = stringResource(R.string.launch)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckBoxRow(
    isChecked: Boolean,
    onChanged: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable {
                onChanged(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // https://stackoverflow.com/questions/71609051/remove-default-padding-around-checkboxes-in-jetpack-compose-new-update
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = null,
            )
        }
        Spacer(Modifier.size(8.dp))
        Text(label)
    }
}

@Composable
fun Alert(
    onDismiss: () -> Unit,
) {
    val dialogScrollState = rememberScrollState()
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                modifier = Modifier.clickable { onDismiss() },
                text = stringResource(android.R.string.ok)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(dialogScrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.app_shortcuts),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(R.string.app_shortcuts_desc),
                    textAlign = TextAlign.Justify,
                )
            }
        },
    )
}