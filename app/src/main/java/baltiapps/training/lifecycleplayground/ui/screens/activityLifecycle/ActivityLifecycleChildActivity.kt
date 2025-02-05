package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import baltiapps.training.lifecycleplayground.R

class ActivityLifecycleChildActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.child_activity_layout)
    }
}