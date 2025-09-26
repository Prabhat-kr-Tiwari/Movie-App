package com.prabhat.movieapp.presentation.screen.more.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prabhat.movieapp.R
import com.prabhat.movieapp.component.MobileStorageBar
import com.prabhat.movieapp.component.SettingsSelectionItem
import com.prabhat.movieapp.component.SettingsToggleItem
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun SettingsScreen(
    innerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {


    Scaffold(
        modifier = modifier
            .padding(innerPaddingValues)
            .background(MaterialTheme.colorScheme.surface), topBar = {
            TopAppBar(
                modifier = Modifier,
                contentColor = MaterialTheme.colorScheme.surface,
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()

                ) {
                    // Back Icon - aligned to the start
                    IconButton(
                        onClick = { onNavigateUp() },
                        modifier = Modifier.align(Alignment.CenterStart),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = MaterialTheme.colorScheme.onBackground,
                            disabledContainerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "back"
                        )
                    }

                    // Centered Text
                    Text(
                        text = "Settings",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }


            }


        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            var isAutoplayEnabled by remember { mutableStateOf(false) }
            var isPushNotificationEnabled by remember { mutableStateOf(true) }


            var isAutoDeleteUponCompletionEnabled by remember { mutableStateOf(false) }
            var isDownloadOnWifiEnabled by remember { mutableStateOf(true) }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "General Setting",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Gray,

                )
            LazyColumn {
                item {
                    SettingsToggleItem(
                        title = "Autoplay",
                        isChecked = isAutoplayEnabled,
                        onCheckedChange = { isAutoplayEnabled = it }
                    )
                }
                item {
                    SettingsToggleItem(
                        title = "Push Notifications",
                        isChecked = isPushNotificationEnabled,
                        onCheckedChange = { isPushNotificationEnabled = it }
                    )
                }

                item {
                    Spacer(
                        Modifier
                            .padding(PaddingValues(horizontal = 16.dp, vertical = 20.dp))
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray)
                    )
                }




                item {
                    Text(
                        text = "Download Preferences",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Gray,

                        )
                }






                item {
                    SettingsToggleItem(
                        title = "Autodelete upon completion",
                        isChecked = isAutoDeleteUponCompletionEnabled,
                        onCheckedChange = { isAutoDeleteUponCompletionEnabled = it }
                    )
                }
                item {
                    SettingsToggleItem(
                        title = "Download only with Wi-Fi",
                        isChecked = isDownloadOnWifiEnabled,
                        onCheckedChange = { isDownloadOnWifiEnabled = it }
                    )
                }

                item {
                    Spacer(
                        Modifier
                            .padding(PaddingValues(horizontal = 16.dp, vertical = 20.dp))
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray)
                    )
                }


                item {
                    Text(
                        text = "Download Video Quality",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Gray,

                        )
                }



                item {
                    SettingsSelectionItem(
                        title = "High Definition",
                        subtitle = "Uses more data",
                        isSelected = true
                    ) {

                    }
                }
                item {
                    SettingsSelectionItem(
                        title = "Standard Definition",
                        subtitle = "Uses less data",
                        isSelected = false
                    ) {

                    }
                }

                item {
                    Spacer(
                        Modifier
                            .padding(PaddingValues(horizontal = 16.dp, vertical = 20.dp))
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray)
                    )
                }


                item {

                    MobileStorageBar(
                        usedFraction = 0.5f,
                        marvelFraction = 0.2f,
                        freeFraction = 0.3f
                    )
                }


            }


        }
    }
}

@ThemeAnnotation
@Composable
fun previewSettingsScreen(modifier: Modifier = Modifier) {
    MovieAppTheme {

        SettingsScreen(onNavigateUp = {}, innerPaddingValues = PaddingValues())
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES


)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO

)
annotation class ThemeAnnotation