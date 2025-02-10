package baltiapps.training.lifecycleplayground.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBubble(
    text: String
) {
    val tooltipPosition = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = tooltipPosition,
        tooltip = {
            PlainTooltip(caretProperties = TooltipDefaults.caretProperties) {
                Text(text)
            }
        },
        state = tooltipState,
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    scope.launch { tooltipState.show() }
                },
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info",
        )
    }
}
