package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActivityLifecycleViewModel(
    private val sharedPreferences: SharedPreferences,
): ViewModel() {

    companion object {
        const val HISTORY = "HISTORY"
        const val COMPOSE_HISTORY = "COMPOSE_HISTORY"
    }

    var activityStateHistory by mutableStateOf(
        sharedPreferences.getStringSet(HISTORY, setOf())?.toSet() ?: setOf()
    )
    private set

    var composeLifecycleHistory by mutableStateOf(
        sharedPreferences.getStringSet(COMPOSE_HISTORY, setOf())?.toSet() ?: setOf()
    )
    private set

    private val editor = sharedPreferences.edit()

    private fun getTimeStamp(): String {
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        return sdf.format(date)
    }

    fun pushToActivityHistory(state: String) {
        val stateWithTimeStamp = "${getTimeStamp()} : $state"
        activityStateHistory = activityStateHistory + stateWithTimeStamp
        editor.putStringSet(HISTORY, activityStateHistory).apply()
    }

    fun pushToComposeHistory(state: String) {
        val stateWithTimeStamp = "${getTimeStamp()} : $state"
        composeLifecycleHistory = composeLifecycleHistory + stateWithTimeStamp
        editor.putStringSet(COMPOSE_HISTORY, composeLifecycleHistory).apply()
    }

    fun clearHistory() {
        activityStateHistory = setOf()
        composeLifecycleHistory = setOf()
        editor.putStringSet(HISTORY, setOf()).apply()
        editor.putStringSet(COMPOSE_HISTORY, setOf()).apply()
    }
}