package baltiapps.training.lifecycleplayground.ui.screens.activityStacks

import android.os.Bundle
import baltiapps.training.lifecycleplayground.R

class ActivityD: GenericStackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            activityName = "Activity D",
            activityObjectId = this.getId(),
            activityDescriptions = listOf(
                getString(R.string.activity_desc_D_1),
                getString(R.string.activity_desc_D_2),
                getString(R.string.activity_desc_D_3),
            )
        )
    }
}