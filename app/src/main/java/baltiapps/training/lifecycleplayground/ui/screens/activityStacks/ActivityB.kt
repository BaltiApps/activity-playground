package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Bundle
import baltiapps.training.lifecycleplayground.R

class ActivityB: GenericStackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            activityName = "Activity B",
            activityObjectId = this.getId(),
            activityDescriptions = listOf(
                getString(R.string.activity_desc_B_1),
                getString(R.string.activity_desc_B_2),
                getString(R.string.activity_desc_B_3),
            )
        )
    }
}