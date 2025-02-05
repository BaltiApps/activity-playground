package baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import baltiapps.training.lifecycleplayground.R
import baltiapps.training.lifecycleplayground.ui.theme.LifecyclePlaygroundTheme

class ActivityLifecycleChildActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifecyclePlaygroundTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.android_icon),
                        contentDescription = null,
                    )
                    Text(stringResource(R.string.child_activity_desc))
                }
            }
        }
    }
}