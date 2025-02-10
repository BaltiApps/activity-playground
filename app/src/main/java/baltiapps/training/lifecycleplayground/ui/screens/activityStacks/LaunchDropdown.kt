package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import baltiapps.training.lifecycleplayground.R
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDropdown(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    activityList: List<LaunchInfo<*>>,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = activityList[selectedIndex].label,
            onValueChange = {},
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            activityList.forEachIndexed { index, launchInfo ->
                DropdownMenuItem(
                    text = {
                        Text(launchInfo.label)
                    },
                    onClick = {
                        isExpanded = false
                        onItemSelected(index)
                    },
                )
            }
        }
    }
}

data class LaunchInfo<T: Activity>(
    val activityClass: KClass<T>?,
    val label: String = activityClass?.simpleName ?: "",
    val flags: List<Int> = listOf(),
) {
    fun Context.launch() {
        activityClass ?: return
        val launchIntent = Intent(this, activityClass.java)
        flags.forEach {
            launchIntent.addFlags(it)
        }
        this.startActivity(launchIntent)
    }
}

@Composable
fun activityList() = listOf(
    LaunchInfo(
        activityClass = null,
        label = stringResource(R.string.select_activity),
    ),
    LaunchInfo(
        activityClass = ActivityA::class,
        label = stringResource(R.string.standard_activity),
    ),
    LaunchInfo(
        activityClass = ActivityB::class,
        label = stringResource(R.string.single_top),
    ),
    LaunchInfo(
        activityClass = ActivityC::class,
        label = stringResource(R.string.single_task),
    ),
    LaunchInfo(
        activityClass = ActivityD::class,
        label = stringResource(R.string.single_instance),
    ),
    LaunchInfo(
        activityClass = ActivityE::class,
        label = stringResource(R.string.single_instance_per_task),
    ),
)