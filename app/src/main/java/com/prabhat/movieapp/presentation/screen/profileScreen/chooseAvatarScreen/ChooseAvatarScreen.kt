package com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.prabhat.movieapp.navigation.ProfileDestination
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen.BillingDetailsScreen
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseAvatarScreen(
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

            Modifier .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)

                .fillMaxSize()
               ,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .height(10.dp))
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
                        color =  MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 80.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Choose Your Avatar",
                    fontWeight = FontWeight.Bold,
                    color =  MaterialTheme.colorScheme.onBackground,
                    fontSize = 22.sp,
                )
            }




            Spacer(modifier = Modifier.height(20.dp))


            val rows = 2
            val columns = 4

            var selectedAvatar by remember { mutableIntStateOf(-1) }

            FlowRow(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                maxItemsInEachRow = rows
            ) {
                val itemModifier = Modifier
                    .padding(10.dp)
                    .size(100.dp) // Size of the circular avatar
                    .clip(CircleShape) // Make it circular
                    .border(BorderStroke(1.dp, Color.White), CircleShape) // Add a black border


                // Sample image resources (replace with your actual image resources or URLs)
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

                // Repeat to fill grid with images
                imageResources.forEachIndexed { index, image ->
                    val itemSize =
                        if (selectedAvatar == index) 120.dp else 100.dp // Increase size if selected
                    val selectedColor = if (selectedAvatar == index) Color.Green else Color.White

                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(itemSize) // Size of the circular avatar
                            .clip(CircleShape) // Make it circular
                            .border(BorderStroke(1.dp, selectedColor), CircleShape)
                            .clickable { selectedAvatar = index },
                        contentAlignment = Alignment.Center // Center the image inside the box
                    ) {
                        Image(
                            painter = image,
                            contentDescription = "Avatar Image",
                            modifier = Modifier.size(100.dp) // Adjust image size within the circle
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                var islooksGoodClicked by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(islooksGoodClicked) {
                    if (islooksGoodClicked) {
                        navHostController.navigate(ProfileDestination.EnterUserNameScreen)
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
                                islooksGoodClicked = !islooksGoodClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (islooksGoodClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (islooksGoodClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (islooksGoodClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (islooksGoodClicked) Color.Black else Color.White
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
                                    text = "Looks Good",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp, textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.onBackground

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
fun previewChooseAvatarScreen(){

    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    MovieAppTheme {
        // Preview of your screen with a placeholder statusBarColor
        ChooseAvatarScreen(
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


