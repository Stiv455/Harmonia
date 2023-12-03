package com.kyant.music.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.music.storage.mediaStore
import com.kyant.music.ui.AppScreen
import com.kyant.music.ui.core.Card
import com.kyant.music.ui.core.CardItem
import com.kyant.ui.FilledTonalButton
import com.kyant.ui.Icon
import com.kyant.ui.Surface
import com.kyant.ui.Text
import com.kyant.ui.navigation.currentNavigator
import com.kyant.ui.style.shape.Rounding
import com.kyant.ui.style.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LibraryState.LibraryMenu(modifier: Modifier = Modifier) {
    val navigator = currentNavigator<AppScreen>()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Headline(
            text = "Library",
            modifier = Modifier.padding(top = 24.dp)
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Surface(
                shape = Rounding.Large.asSmoothRoundedShape()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Scan your library",
                        modifier = Modifier.padding(16.dp, 8.dp),
                        style = typography.headlineSmall
                    )
                    FilledTonalButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                mediaStore.scan()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        enabled = !mediaStore.isScanning
                    ) {
                        Text(text = "Scan")
                    }
                }
            }

            Card {
                val scope = rememberCoroutineScope()

                CardItem(
                    onClick = {
                        scope.launch {
                            navigate(ListPaneRoute.Songs)
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp, 8.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {}
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Songs",
                                style = typography.bodyLarge
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.NavigateNext,
                            emphasis = 0.5f
                        )
                    }
                }
                CardItem(
                    onClick = {
                        scope.launch {
                            navigate(ListPaneRoute.Albums)
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp, 8.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {}
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Albums",
                                style = typography.bodyLarge
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.NavigateNext,
                            emphasis = 0.5f
                        )
                    }
                }
                CardItem(
                    onClick = { navigator?.push(AppScreen.Settings) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp, 8.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {}
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Settings",
                                style = typography.bodyLarge
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.NavigateNext,
                            emphasis = 0.5f
                        )
                    }
                }
            }
        }
    }
}
