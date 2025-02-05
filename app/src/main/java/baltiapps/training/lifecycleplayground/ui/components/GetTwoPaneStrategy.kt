package baltiapps.training.lifecycleplayground.ui.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPaneStrategy
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy

@Composable
fun getTwoPaneStrategy(): TwoPaneStrategy {
    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
    return if (isLandscape) {
        HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp)
    } else {
        VerticalTwoPaneStrategy(splitFraction = 0.7f, gapHeight = 16.dp)
    }
}