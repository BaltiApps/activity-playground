package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Bundle
import baltiapps.training.lifecycleplayground.R

class ActivityE: GenericStackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            activityName = "Activity E",
            activityObjectId = this.getId(),
            activityDescriptions = listOf(
                getString(R.string.activity_desc_E_1),
                getString(R.string.activity_desc_E_2),
                getString(R.string.activity_desc_E_3),
            )
        )
    }
}