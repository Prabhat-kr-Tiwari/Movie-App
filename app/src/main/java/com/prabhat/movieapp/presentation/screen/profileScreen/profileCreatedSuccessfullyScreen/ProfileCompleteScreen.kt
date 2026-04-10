package com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.R
import com.prabhat.movieapp.navigation.BottomNavigationDestination
import com.prabhat.movieapp.navigation.SubGraph
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import java.util.Locale
import java.util.Locale.getDefault

@Composable
fun ProfileCompleteScreen(
    innerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    profileCompleteScreenViewModel: ProfileCompleteScreenViewModel= hiltViewModel()
) {
    val uiState= profileCompleteScreenViewModel.uiState.collectAsStateWithLifecycle()
    val imageResources = listOf(
        painterResource(id = R.drawable.ellipse13),
        painterResource(id = R.drawable.ellipse14),
        painterResource(id = R.drawable.ellipse15),
        painterResource(id = R.drawable.ellipse16),
        painterResource(id = R.drawable.ellipse17),
        painterResource(id = R.drawable.ellipse18),
        painterResource(id = R.drawable.ellipse19),
        painterResource(id = R.drawable.ellipse20)
    )
    val avatarIndex = uiState.value.avatarId?.toInt()
    val painterResource = imageResources[avatarIndex ?: 0]

    Scaffold(
        modifier = modifier

            .background(MaterialTheme.colorScheme.surface)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)

            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .height(10.dp))

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .height(120.dp)
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MOVIES",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Your Profile is Created\n        Successfully!!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(8.dp))




                Box(
                    modifier = Modifier

                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.property_1_frame_5),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(180.dp)
                    )

                    Image(
                        painter = painterResource,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(160.dp).align(Alignment.Center)
                    )

                }


                Spacer(modifier = Modifier.height(40.dp))



                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.value.userName.toString().uppercase(getDefault()),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )


            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var isIAmSafeAllNowClicked by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(isIAmSafeAllNowClicked) {
                    if (isIAmSafeAllNowClicked) {
                        navHostController.navigate(BottomNavigationDestination.MovieHomeScreen){
                            popUpTo(SubGraph.Profile) { inclusive=true }
                        }
                        profileCompleteScreenViewModel.onEvent(ProfileCreatedEvent.onProfileCreatedDone)
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                isIAmSafeAllNowClicked =
                                    !isIAmSafeAllNowClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isIAmSafeAllNowClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isIAmSafeAllNowClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (isIAmSafeAllNowClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (isIAmSafeAllNowClicked) Color.Black else Color.White
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 20.dp,
                                pressedElevation = 30.dp,
                                focusedElevation = 30.dp,
                                hoveredElevation = 30.dp,
                                disabledElevation = 0.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Eat Your Green Vegetables",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onBackground

                                )

                            }

                        }


                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

        }
    }

}

@ThemeAnnotation
@Composable
fun PreviewProfileCompleteScreen(modifier: Modifier = Modifier) {

    // Mock NavHostController for the preview
    val navController = rememberNavController()

    MovieAppTheme {
        // Mock SystemUiController (You might need to provide a proper implementation or mock)
        val systemUiController = rememberSystemUiController()
        ProfileCompleteScreen(
            innerPaddingValues=PaddingValues(20.dp),
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Red
        )
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