package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import com.prabhat.movieapp.ui.theme.MovieAppTheme


@Composable
fun ChooseYourPlanScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color
) {

    Scaffold(
        modifier = modifier

            .background(MaterialTheme.colorScheme.surface)
    ) { innerPadding ->
        systemUiController.setStatusBarColor(color = statusBarColor)
        Column(
            modifier = modifier
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 40.dp, end = 40.dp)
                    .background(MaterialTheme.colorScheme.surface),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)

                        .clip(CircleShape)
                        .background(Color.Red)
                        .size(24.dp), contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "1",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .width(50.dp)
                        .background(Color.Red.copy(alpha = 0.5f))
                        .height(4.dp)
                )

                Box(
                    modifier = Modifier

                        .padding(start = 5.dp, end = 5.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.5f))

                        .size(24.dp), contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "2",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .width(50.dp)
                        .background(Color.Red.copy(alpha = 0.5f))
                        .height(4.dp)
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                )


                Box(
                    modifier = Modifier

                        .padding(start = 5.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.5f))
                        .size(24.dp), contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "3",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            )

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
                text = "Choose your Plan",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
            Text(
                text = "Cancel at any time",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            var isClicked by remember { mutableStateOf(false) } // State to track button click
            var isMovieClicked by remember { mutableStateOf(false) } // State to track button click
            var isSeriesClicked by remember { mutableStateOf(false) } // State to track button click

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
                , modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                isClicked = !isClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (isClicked) Color.Red else Color.Black,
                                contentColor =Color.White,
                                disabledContentColor = if (isClicked) Color.Black else Color.White
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
                                modifier = Modifier.fillMaxSize()

                                ,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Movies & Series",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "$20/month",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.End,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }


                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                isMovieClicked = !isMovieClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isMovieClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isMovieClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (isMovieClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (isMovieClicked) Color.Black else Color.White
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
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Movies",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "$15/month",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.End,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }


                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                isSeriesClicked = !isSeriesClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isSeriesClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSeriesClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (isSeriesClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (isSeriesClicked) Color.Black else Color.White
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
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Series",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "$15/month",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.End,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }



                    }
                }


                //adding continue button

                var isContinueClicked by remember { mutableStateOf(false) } // State to track button click

                Log.d("PRABHAT1", "ChooseYourPlanScreen: $isContinueClicked")
                LaunchedEffect(isContinueClicked) {
                    Log.d("PRABHAT LaunchedEffect", "ChooseYourPlanScreen: ")
                    if (isContinueClicked) {
                        Log.d("PRABHAT isContinueClicked", "ChooseYourPlanScreen: ")
                        navHostController.navigate(PlansAndPaymentDestination.ChooseYourPaymentModeScreen)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(top = 130.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                ) {
                    Column(
                        modifier = Modifier

                            .align(Alignment.Center)
                            .fillMaxWidth()

                    ) {

                        Button(
                            onClick = {
                                isContinueClicked = !isContinueClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isContinueClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isContinueClicked) Color.Red else MaterialTheme.colorScheme.background,
                                disabledContainerColor = if (isContinueClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (isContinueClicked) Color.Black else Color.White
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
                                modifier = Modifier

                                    .fillMaxSize()
                                ,
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Continue",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                            }

                        }

                        Spacer(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .height(12.dp)

                        ) // Add space between the button and text

                    }
                }


            }





        }
    }

}

@ThemeAnnotation
@Composable
fun ChooseYourPlanScreenPreview() {

    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()
    MovieAppTheme {
// Preview of your screen with a placeholder statusBarColor
        ChooseYourPlanScreen(
            modifier = Modifier,
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Blue // or any other color
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
