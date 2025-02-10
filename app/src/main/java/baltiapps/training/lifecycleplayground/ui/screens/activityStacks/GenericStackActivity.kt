package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import baltiapps.training.lifecycleplayground.ui.theme.LifecyclePlaygroundTheme

abstract class GenericStackActivity: ComponentActivity() {
    fun setContent(
        activityName: String,
        activityObjectId: String,
        activityDescriptions: List<String>,
    ) {
        enableEdgeToEdge()
        setContent {
            LifecyclePlaygroundTheme {
                StackCommonLayout(
                    activityName = activityName,
                    activityObjectId = activityObjectId,
                    activityDescriptions = activityDescriptions,
                    getAppTasksList = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            getAppTasksList()
                        } else listOf()
                    },
                    getRunningTasksList = { getRunningTasksList() },
                    getActivitiesCount = { getActivitiesCount() },
                    getBaseActivity = { getTaskBase() }
                )
            }
        }
    }
}