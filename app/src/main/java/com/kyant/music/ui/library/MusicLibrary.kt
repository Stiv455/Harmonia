package com.kyant.music.ui.library

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import com.kyant.music.ui.AppScreen
import com.kyant.ui.BoxNoInline
import com.kyant.ui.navigation.OnBackPressed
import com.kyant.ui.navigation.currentNavigator
import com.kyant.ui.style.motion.Duration
import com.kyant.ui.style.motion.Easing
import com.kyant.ui.style.motion.Easing.with
import com.kyant.ui.util.lerp
import kotlin.math.roundToInt

@Composable
fun MusicLibrary() {
    val navigator = currentNavigator<AppScreen>()
    val libraryNavigator = rememberLibraryNavigator()

    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(activity = context as Activity)

    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        BoxNoInline(
            modifier = Modifier.draggable(
                state = libraryNavigator.draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = { velocity -> libraryNavigator.fling(velocity) },
                reverseDirection = true
            )
        ) {
            BoxNoInline(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeable.placeRelative(
                                ((0 - libraryNavigator.paneExpandProgressValue) * constraints.maxWidth).roundToInt(),
                                0
                            )
                        }
                    }
                    .graphicsLayer {
                        alpha = lerp(1f, 0f, libraryNavigator.paneExpandProgressValue)
                    }
            ) {
                libraryNavigator.Home(navigator = navigator)
            }

            BoxNoInline(
                modifier = Modifier.layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        placeable.placeRelative(
                            ((1 - libraryNavigator.paneExpandProgressValue) * constraints.maxWidth).roundToInt(),
                            0
                        )
                    }
                }
            ) {
                libraryNavigator.Songs()
            }
        }
    } else {
        BoxNoInline(
            modifier = Modifier.layout { measurable, constraints ->
                val fraction = lerp(1f, 1f / 3f, libraryNavigator.paneExpandProgressValue)
                val maxWidth = (fraction * libraryNavigator.width).roundToInt()
                val placeable = measurable.measure(constraints.copy(maxWidth = maxWidth))
                layout(maxWidth, constraints.maxHeight) {
                    placeable.placeRelative(0, 0)
                }
            }
        ) {
            libraryNavigator.Home(navigator = navigator)
        }

        AnimatedVisibility(
            visible = libraryNavigator.targetPaneExpandProgress == 1,
            modifier = Modifier.layout { measurable, constraints ->
                val fraction = 1f - lerp(1f, 1f / 3f, libraryNavigator.paneExpandProgressValue)
                val endMaxWidth = (2f / 3f * libraryNavigator.width).roundToInt()
                val paddingStart = ((1f - fraction) * libraryNavigator.width).roundToInt()
                val placeable = measurable.measure(constraints.copy(maxWidth = endMaxWidth))
                layout(endMaxWidth, constraints.maxHeight) {
                    placeable.placeRelative(paddingStart, 0)
                }
            },
            enter = fadeIn(Easing.EmphasizedAccelerate with Duration.SHORT_4),
            exit = fadeOut(Easing.EmphasizedDecelerate with Duration.SHORT_4)
        ) {
            libraryNavigator.Songs()
        }
        OnBackPressed(enabled = { libraryNavigator.targetPaneExpandProgress == 1 }) {
            libraryNavigator.collapsePane()
        }
    }
}
