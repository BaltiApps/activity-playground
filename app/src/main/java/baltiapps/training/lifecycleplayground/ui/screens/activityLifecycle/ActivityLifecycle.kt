package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import baltiapps.training.lifecycleplayground.R
import baltiapps.training.lifecycleplayground.ui.theme.LifecyclePlaygroundTheme

class ActivityLifecycle: ComponentActivity() {

    private val sharedPreferences by lazy {
        this.getPreferences(MODE_PRIVATE)
    }
    private val viewModel by viewModels<ActivityLifecycleViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ActivityLifecycleViewModel(sharedPreferences) as T
                }
            }
        }
    )

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_title)
            .setMessage(R.string.alert_dialog_message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun launchChildActivity() {
        startActivity(
            Intent(this, ChildActivity::class.java)
        )
    }

    private fun launchDialogActivity() {
        startActivity(
            Intent(this, DialogActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.pushToActivityHistory("onCreate")
        enableEdgeToEdge()
        setContent {
            LifecyclePlaygroundTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main",
                ) {
                    composable(route = "main") {
                        MainComposable(
                            viewModel = viewModel,
                            showDialog = ::showDialog,
                            onBack = {
                                onBackPressedDispatcher.onBackPressed()
                            },
                            launchChildComposable = {
                                navController.navigate("child_composable")
                            },
                            launchChildActivity = ::launchChildActivity,
                            launchDialogActivity = ::launchDialogActivity,
                            launchDialogComposable = {
                                navController.navigate("dialog_composable")
                            }
                        )
                    }
                    composable(route = "child_composable") {
                        ChildComposable()
                    }
                    dialog(route = "dialog_composable") {
                        DialogComposable()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.pushToActivityHistory("onStart")
    }

    override fun onResume() {
        super.onResume()
        viewModel.pushToActivityHistory("onResume")
    }

    override fun onPause() {
        viewModel.pushToActivityHistory("onPause")
        super.onPause()
    }

    override fun onStop() {
        viewModel.pushToActivityHistory("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.pushToActivityHistory("onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.pushToActivityHistory("onRestart")
    }
}