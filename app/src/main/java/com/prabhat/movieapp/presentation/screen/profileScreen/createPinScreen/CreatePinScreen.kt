package com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.prabhat.movieapp.presentation.components.OtpTextField
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun CreatePinScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color
) {
    Scaffold(
        modifier = modifier

            .background(MaterialTheme.colorScheme.surface)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)

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
                    text = "Create a Pin",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
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


                }


                Spacer(modifier = Modifier.height(40.dp))

                /*  Row(
                horizontalArrangement = Arrangement.SpaceEvenly
                , verticalAlignment = Alignment.CenterVertically
                , modifier = Modifier.fillMaxWidth()
            ) {


                Row ( horizontalArrangement = Arrangement.SpaceEvenly
                    , verticalAlignment = Alignment.CenterVertically
                    , modifier = Modifier
                        .height(80.dp)
                        .width(100.dp)){

                    val otp1State = rememberTextFieldState()

                    BasicTextField(
                        state = otp1State,

                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Gray)
                            .padding(10.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),

                        decorator = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {



                                Box(modifier = Modifier.weight(1f)) {

                                    if (otp1State.text.isEmpty()) {
                                        Text(
                                            text = "",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }

                            }

                        }

                    )


                }

                Row( horizontalArrangement = Arrangement.SpaceEvenly
                    , verticalAlignment = Alignment.CenterVertically
                    , modifier = Modifier
                        .height(80.dp)
                        .width(100.dp)) {


                    val emailIdState = rememberTextFieldState()

                    BasicTextField(
                        state = emailIdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Gray)
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
                        decorator = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {



                                Box(modifier = Modifier.weight(1f)) {

                                    if (emailIdState.text.isEmpty()) {
                                        androidx.compose.material3.Text(
                                            text = "",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (emailIdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    androidx.compose.material3.Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            emailIdState.edit {
                                                replace(0, emailIdState.text.length, "")
                                            }
                                        })
                                }


                            }

                        }

                    )
                }

                Row( horizontalArrangement = Arrangement.SpaceEvenly
                    , verticalAlignment = Alignment.CenterVertically
                    , modifier = Modifier
                        .height(80.dp)
                        .width(100.dp)) {


                    val emailIdState = rememberTextFieldState()

                    BasicTextField(
                        state = emailIdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Gray)
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
                        decorator = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {



                                Box(modifier = Modifier.weight(1f)) {

                                    if (emailIdState.text.isEmpty()) {
                                        androidx.compose.material3.Text(
                                            text = "",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (emailIdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    androidx.compose.material3.Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            emailIdState.edit {
                                                replace(0, emailIdState.text.length, "")
                                            }
                                        })
                                }


                            }

                        }

                    )
                }
                Row( horizontalArrangement = Arrangement.SpaceEvenly
                    , verticalAlignment = Alignment.CenterVertically
                    , modifier = Modifier
                        .height(80.dp)
                        .width(100.dp)) {


                    val emailIdState = rememberTextFieldState()

                    BasicTextField(
                        state = emailIdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Gray)
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
                        decorator = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {



                                Box(modifier = Modifier.weight(1f)) {

                                    if (emailIdState.text.isEmpty()) {
                                        androidx.compose.material3.Text(
                                            text = "",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (emailIdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    androidx.compose.material3.Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            emailIdState.edit {
                                                replace(0, emailIdState.text.length, "")
                                            }
                                        })
                                }


                            }

                        }

                    )
                }




            }*/
                var otpValue by remember {
                    mutableStateOf("")
                }

                OtpTextField(
                    otpText = otpValue,
                    onOtpTextChange = { value, otpInputFilled ->
                        otpValue = value
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "This pin will be used \nto log-in to your profile",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
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
                        navHostController.navigate(ProfileDestination.ProfileCompleteScreen)

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
                                    text = "I’m all safe now",
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
fun previewCreatePinScreen(modifier: Modifier = Modifier) {


    // Mock NavHostController for the preview
    val navController = rememberNavController()

    MovieAppTheme {
        // Mock SystemUiController (You might need to provide a proper implementation or mock)
        val systemUiController = rememberSystemUiController()
        CreatePinScreen(
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
//https://github.com/banmarkovic/OtpTextField