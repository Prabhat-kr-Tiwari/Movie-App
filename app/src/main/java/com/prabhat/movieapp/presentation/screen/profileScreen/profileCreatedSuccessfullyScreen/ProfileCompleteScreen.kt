package com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.R
import com.prabhat.movieapp.navigation.BottomNavigationDestination

@Composable
fun ProfileCompleteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeDrawing)

            .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .height(200.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MOVIES",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 80.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Your Profile is Created\n        Successfully!!",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
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
                    painter = painterResource(id = R.drawable.ellipse13),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(160.dp).align(Alignment.Center)
                )

            }


            Spacer(modifier = Modifier.height(40.dp))



            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "UIUXDIVYANSHU",
                color = Color.White,
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
                    navHostController.navigate(BottomNavigationDestination.MovieHomeScreen)
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
                            containerColor = if (isIAmSafeAllNowClicked) Color.Red else Color.Black,
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
                                fontSize = 18.sp, textAlign = TextAlign.Start
                            )

                        }

                    }


                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileCompleteScreen(modifier: Modifier = Modifier) {

    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()
    ProfileCompleteScreen(
        navHostController = navController,
        systemUiController = systemUiController,
        statusBarColor = Color.Red
    )
}