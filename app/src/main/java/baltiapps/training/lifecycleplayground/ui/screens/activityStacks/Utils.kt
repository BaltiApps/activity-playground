package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi

private fun getActivityManager(context: Context): ActivityManager {
    return context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Activity.getAppTasksList(): List<Int> {
    val activityManager = getActivityManager(this)
    val list = mutableListOf<Int>()
    val tasks = activityManager.appTasks
    tasks
        .map { it.taskInfo }
        .forEach { list.add(it.taskId) }
    return list
}

fun Activity.getRunningTasksList(): List<Int> {
    val activityManager = getActivityManager(this)
    val list = mutableListOf<Int>()
    val runningTasks = activityManager.getRunningTasks(Int.MAX_VALUE)
    runningTasks.forEach { list.add(it.id) }
    return list
}

fun Activity.getActivitiesCount(): Int {
    val activityManager = getActivityManager(this)
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val runningTasks = activityManager.getRunningTasks(Int.MAX_VALUE)
        runningTasks.find { it.id == taskId }?.numActivities ?: 0
    } else {
        val tasks = activityManager.appTasks
        tasks
            .map { it.taskInfo }
            .find { it.taskId == taskId }?.numActivities ?: 0
    }
}

fun Activity.getId(): String {
    return this.toString().run {
        if (contains('@')) this.split('@')[1]
        else this
    }
}

fun Activity.getTaskBase(): String {
    val activityManager = getActivityManager(this)

    val baseActivity = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val runningTasks = activityManager.getRunningTasks(100)
        runningTasks.find { it.id == taskId }?.baseActivity
    } else {
        val tasks = activityManager.appTasks
        tasks
            .map { it.taskInfo }
            .find { it.taskId == taskId }?.baseActivity
    }

    val className = baseActivity?.shortClassName ?: ""
    return className.split('.').last()
}