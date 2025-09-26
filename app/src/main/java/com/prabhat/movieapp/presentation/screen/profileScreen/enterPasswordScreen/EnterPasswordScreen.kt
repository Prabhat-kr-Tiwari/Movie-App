package com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
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
import com.prabhat.movieapp.R
import com.prabhat.movieapp.navigation.ProfileDestination
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun EnterPasswordScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color
) {
    Scaffold(  modifier = modifier .windowInsetsPadding(WindowInsets.safeDrawing)

        .background(MaterialTheme.colorScheme.surface)) {innerPadding->


        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface),
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
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 80.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Choose a Password",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))




                Box(
                    modifier = Modifier

                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(end = 70.dp, start = 70.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ellipse13),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(140.dp)
                        )


                    }
                    Text(
                        text = "Change",
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding()
                            .align(Alignment.BottomEnd)
                            .clickable {
                                navHostController.popBackStack()
                            }

                    )

                }


                Spacer(modifier = Modifier.height(40.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    val passwordState = rememberTextFieldState()

                    BasicTextField(
                        state = passwordState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainerHighest
                            })
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp
                        ),
                        decorator = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .padding(6.dp)
                                )
                                /*Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable {}
                                    .padding(6.dp),
                            ) {

                                Icon(
                                    imageVector = Icons.Rounded.AccountBox,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    contentDescription = null
                                )
                            }*/

                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {

                                    if (passwordState.text.isEmpty()) {
                                        Text(
                                            text = "Create a password",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (passwordState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            passwordState.edit {
                                                replace(0, passwordState.text.length, "")
                                            }
                                        })
                                }


                            }

                        }

                    )
                    var colorStateOfValidation by remember {
                        mutableStateOf(Color.Gray)
                    }
                    if (passwordState.text.isNotEmpty()) {


                        if (isPasswordValid(password = passwordState.text.toString())) {
                            colorStateOfValidation = Color.Green
                        } else {
                            colorStateOfValidation =    MaterialTheme.colorScheme.onBackground

                        }

                    }
                    Text(
                        text = "Minimum 8 characters\n" +
                                "Only A-Z, a-z, 0-9\n" +
                                "1 special charcter (. , _ - / &)",
                        color = colorStateOfValidation,
                        modifier = Modifier.padding(horizontal = 40.dp, vertical = 4.dp)
                    )
                }

//            Spacer(modifier = Modifier.height(20.dp))


            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var isLooksStrongClicked by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(isLooksStrongClicked) {
                    if (isLooksStrongClicked) {
                        navHostController.navigate(ProfileDestination.CreatePinScreen)

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
                                isLooksStrongClicked =
                                    !isLooksStrongClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isLooksStrongClicked) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isLooksStrongClicked) Color.Red else MaterialTheme.colorScheme.surface,
                                disabledContainerColor = if (isLooksStrongClicked) Color.Red else Color.Black,
                                contentColor = Color.White,
                                disabledContentColor = if (isLooksStrongClicked) Color.Black else Color.White
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
                                    text = "Looks Strong",
                                    color = MaterialTheme.colorScheme.onBackground,
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

}

fun isPasswordValid(password: String): Boolean {
    // Regular expression for password validation
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[.,_\\-/&]).{8,}\$")

    return password.matches(passwordRegex)
}

@ThemeAnnotation
@Composable
fun PreviewEnterPasswordScreen(modifier: Modifier = Modifier) {
    // Mock NavHostController for the preview
    val navController = rememberNavController()

    MovieAppTheme {
        // Mock SystemUiController (You might need to provide a proper implementation or mock)
        val systemUiController = rememberSystemUiController()
        EnterPasswordScreen(
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Red
        )
    }


}
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
            showSystemUi = true


)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true

)
annotation class ThemeAnnotation