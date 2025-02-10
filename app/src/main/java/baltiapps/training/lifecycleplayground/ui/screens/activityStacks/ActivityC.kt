package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Bundle
import baltiapps.training.lifecycleplayground.R

class ActivityC: GenericStackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            activityName = "Activity C",
            activityObjectId = this.getId(),
            activityDescriptions = listOf(
                getString(R.string.activity_desc_C_1),
                getString(R.string.activity_desc_C_2),
                getString(R.string.activity_desc_C_3),
            )
        )
    }
}