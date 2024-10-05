package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
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
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChooseYourPlanScreen


@Composable
fun ChoosePaymentModeScreen(modifier: Modifier = Modifier, navHostController: NavHostController, systemUiController: SystemUiController, statusBarColor: Color) {
    systemUiController.setStatusBarColor(color = statusBarColor)
    Column(modifier = Modifier
        .windowInsetsPadding(WindowInsets.safeDrawing)
        .fillMaxSize()
        .background(Color.Black), verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 40.dp, end = 40.dp)
                .background(Color.Black),
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
                    color = Color.White,
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
                    color = Color.White,
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
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.White))

        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

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
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 70.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(text = "Choose how to pay", color = Color.White, fontWeight = FontWeight.Bold,   textAlign = TextAlign.Center, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        var isClicked by remember { mutableStateOf(false) } // State to track button click

        Column( verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {




            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 20.dp, end = 20.dp)
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
                        border = BorderStroke(1.dp, if (isClicked) Color.Transparent else Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isClicked) Color.Red else Color.Black,
                            disabledContainerColor = if (isClicked) Color.Red else Color.Black,
                            contentColor = Color.White,
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
                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = "Credit / Debit Card",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp, textAlign = TextAlign.Start
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
                            isClicked = !isClicked // Toggle the clicked state

                        },
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, if (isClicked) Color.Transparent else Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isClicked) Color.Red else Color.Black,
                            disabledContainerColor = if (isClicked) Color.Red else Color.Black,
                            contentColor = Color.White,
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
                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = "Netbanking",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp, textAlign = TextAlign.Start
                            )

                        }

                    }



                }
            }
        }




        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Movies & Series $20/month", color = Color.White)
            Text(text = "Change", color = Color.Red)
        }

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {
            var isContinueClicked by remember{mutableStateOf(false)}
            LaunchedEffect(isContinueClicked) {
                if (isContinueClicked) {

                    navHostController.navigate(PlansAndPaymentDestination.BillingDetailsScreen)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                contentAlignment = Alignment.BottomCenter
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
                        border = BorderStroke(1.dp, if (isContinueClicked) Color.Transparent else Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isContinueClicked) Color.Red else Color.Black,
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
                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = "Continue",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp, textAlign = TextAlign.Start
                            )

                        }

                    }


                }
            }
        }


    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChoosePaymentModeScreenPreview() {
    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    // Preview of your screen with a placeholder statusBarColor
    ChoosePaymentModeScreen(
        modifier = Modifier,
        navHostController = navController,
        systemUiController = systemUiController,
        statusBarColor = Color.Blue // or any other color
    )
}