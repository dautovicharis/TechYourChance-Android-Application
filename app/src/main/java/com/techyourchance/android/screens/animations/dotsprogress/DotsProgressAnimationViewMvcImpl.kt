package com.techyourchance.android.screens.animations.dotsprogress

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import com.techyourchance.android.R
import com.techyourchance.android.screens.common.mvcviews.ViewMvcType
import com.techyourchance.android.screens.common.toolbar.MyToolbar

class DotsProgressAnimationViewMvcImpl(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?
): DotsProgressAnimationViewMvc() {

    private val toolbar: MyToolbar
    private val frameContent: FrameLayout
    private val switchViewsCompose: SwitchCompat

    init {
        setRootView(layoutInflater.inflate(R.layout.layout_stacked_cards_animation, parent, false))

        toolbar = findViewById(R.id.toolbar)
        frameContent = findViewById(R.id.frameContent)
        switchViewsCompose = findViewById(R.id.switchViewsCompose)

        toolbar.setNavigateUpListener {
            listeners.map { it.onBackClicked() }
        }

        switchViewsCompose.setOnCheckedChangeListener { _, _ ->
            listeners.map { it.onToggleComposeClicked() }
        }

    }

    override fun setType(viewMvcType: ViewMvcType) {
        frameContent.removeAllViews()
        when(viewMvcType) {
            ViewMvcType.VIEW_BASED -> {
                switchViewsCompose.isChecked = false
                frameContent.addView(DotsProgressView(context))
            }
            ViewMvcType.COMPOSE_BASED -> {
                switchViewsCompose.isChecked = true
                frameContent.addView(ComposeView(context).apply {
                    setContent {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            DotsProgressCompose(
                                modifier = Modifier.fillMaxWidth(0.2f),
                                color = colorResource(id = R.color.main),
                            )
                        }
                    }
                })
            }
        }
    }

}