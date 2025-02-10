package baltiapps.training.lifecycleplayground

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import baltiapps.training.lifecycleplayground.ui.screens.activityLifecycle.ActivityLifecycle
import baltiapps.training.lifecycleplayground.ui.screens.activityStacks.ActivityA
import baltiapps.training.lifecycleplayground.ui.theme.LifecyclePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifecyclePlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(this@MainActivity, ActivityLifecycle::class.java)
                                )
                            }
                        ) {
                            Text(stringResource(R.string.activity_lifecycle))
                        }
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(this@MainActivity, ActivityA::class.java)
                                )
                            }
                        ) {
                            Text(stringResource(R.string.activity_stacks))
                        }
                    }
                }
            }
        }
    }
}