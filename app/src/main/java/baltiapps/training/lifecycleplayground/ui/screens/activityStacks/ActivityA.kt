package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Bundle
import baltiapps.training.lifecycleplayground.R

class ActivityA: GenericStackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            activityName = "Activity A",
            activityObjectId = this.getId(),
            activityDescriptions = listOf(
                getString(R.string.activity_desc_A),
            )
        )
    }
}