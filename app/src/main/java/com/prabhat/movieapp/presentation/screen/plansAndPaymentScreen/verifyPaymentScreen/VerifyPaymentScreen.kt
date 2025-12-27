package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.prabhat.movieapp.navigation.ProfileDestination
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import com.prabhat.movieapp.utils.LoadingAnimation
import kotlinx.coroutines.delay

@Composable
fun VerifyPaymentScreen(
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
            modifier = Modifier   .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
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
                    text = "Verify Payment",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .background(color = MaterialTheme.colorScheme.surface)
                )
                Text(
                    text = "Please wait while we verify your payment from the bank....",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(20.dp)
                )
                var isLoading by remember { mutableStateOf(true) }
                var showCompleteAction by remember { mutableStateOf(false) }
                LaunchedEffect(false) {
                    delay(3000)
                    isLoading = false
                    showCompleteAction = true

                }
                if (isLoading) {

                    LoadingAnimation(circleColor = Color.White)
                } /*else {

                if(showCompleteAction){

//                    showCompleteDialog()

                }
            }*/




                androidx.compose.animation.AnimatedVisibility(
                    visible = showCompleteAction, enter = scaleIn() + expandVertically(
                        expandFrom = Alignment.CenterVertically
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(color = Color.Red), contentAlignment = Alignment.Center
                    ) {

                        var showtick by remember {
                            mutableStateOf(false)
                        }
                        LaunchedEffect(Unit) {
                            delay(1000)
                            showtick = true

                        }
                        val tweak by animateDpAsState(
                            targetValue = 200.dp,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioHighBouncy,
                                stiffness = Spring.StiffnessHigh
                            ),
                            label = ""
                        )
                        this@Column.AnimatedVisibility(
                            visible = showtick,
                            enter = scaleIn() + expandVertically(
                                expandFrom = Alignment.CenterVertically
                            )

                        ) {

                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(tweak)
                                    .padding(10.dp)
                            )

                        }


                    }
                }


            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                var isClicked by remember { mutableStateOf(false) } // State to track button click
                if (isClicked) {
                    LaunchedEffect(Unit) {

                        navHostController.navigate(ProfileDestination.ChooseAvatarScreen)
                    }
                }
//        AnimatedRow()
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
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Continue",
                                    color = MaterialTheme.colorScheme.onBackground,
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

}

@ThemeAnnotation
@Composable
fun VerifyPaymentScreenPreview() {
    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    MovieAppTheme {

        // Preview of your screen with a placeholder statusBarColor
        VerifyPaymentScreen(
            modifier = Modifier,
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Blue // or any other color
        )
    }
}

@ThemeAnnotation
@Composable
fun showCompleteDialog(modifier: Modifier = Modifier) {


    var showAnimation by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .background(color = Color.Red), contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = showAnimation, enter = scaleIn() + expandVertically(
                expandFrom = Alignment.CenterVertically
            )
        ) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .size(200.dp)
                    .padding(10.dp)
            )

        }


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



