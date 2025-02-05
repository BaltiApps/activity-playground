package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import baltiapps.training.lifecycleplayground.R

class DialogActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_activity_layout)
    }
}