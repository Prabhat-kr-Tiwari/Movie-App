package com.prabhat.movieapp.presentation.screen.loginScreen

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapp.utils.CustomLoadingDialog
import com.prabhat.movieapp.R
import com.prabhat.movieapp.navigation.Destination
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val loginScreenState = loginScreenViewModel.authenticateUserState.value

    if (loginScreenState.isLoginSuccessful?.success == true){
        Log.d("PRABHAT", "LoginScreen: ")
       /* LaunchedEffect(key1 = Unit) {

            navHostController.navigate(Destination.SignUpScreen)

        }*/
        LaunchedEffect(Unit) {
            navHostController.navigate(PlansAndPaymentDestination.ChooseYourPlanScreen) /*{
                popUpTo(Destination.LoginScreen) { inclusive = true } // This clears the LoginScreen from the backstack
                launchSingleTop = true // Avoid multiple instances of the same screen
            }*/
        }
    }
    Text(text = "Login")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val TAG = "PRABHAT"

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(40.dp))
                .height(200.dp)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "MOVIES",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 80.sp,
                textAlign = TextAlign.Center
            )
        }


        Spacer(modifier = Modifier.height(20.dp))


        val emailIdState = rememberTextFieldState()

        BasicTextField(
            state = emailIdState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
                .padding(10.dp),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
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
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {

                        if (emailIdState.text.isEmpty()) {
                            androidx.compose.material3.Text(
                                text = "UserName",
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
                        Icon(
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

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .background(Color.Red)
        )

        //
        val passwordState = rememberTextFieldState()
        var isPasswordVisible by remember { mutableStateOf(false) } // State for password visibility


        BasicSecureTextField(
            state = passwordState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
                .padding(10.dp),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
//            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            inputTransformation = InputTransformation { if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() },

            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {}
                            .padding(6.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {

                        if (passwordState.text.isEmpty()) {
                            androidx.compose.material3.Text(
                                text = "Password",
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


        //


        Spacer(modifier = Modifier.height(8.dp))


        var isClicked by remember { mutableStateOf(false) } // State to track button click


        //LOADING SIMULATION
      /*  LaunchedEffect(isClicked) {

            if (isClicked) {
//                delay(8000)
                isClicked = false
            }
        }*/
        if (isClicked) {

            if (emailIdState.text.isNotEmpty()&&passwordState.text.isNotEmpty()){

                loginScreenViewModel.authenticateUser(username = emailIdState.text.toString(), password = passwordState.text.toString())
            }

            if (loginScreenState.isLoading){
                Log.d("PRABHAT", "LoginScreen: loading")
                CustomLoadingDialog(
                    lineHeight = 10,
                    isClicked = isClicked,
                    initialValue = 50,
                    primaryColor = Color.White,
                    secondaryColor = Color.DarkGray,
                    circleRadius = 50f,
                    showPercentage = false,
                    showInnerCircle = false,
                    showProgressCircle = false,
                    showArcOnProgressCircle = false,
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
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
                    androidx.compose.material3.Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp)) // Add space between the button and text

                androidx.compose.material3.Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
        ) {

            Column(
                Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                androidx.compose.material3.Text(
                    text = "Or",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                )
                Spacer(modifier = Modifier.height(2.dp))
                androidx.compose.material3.Text(
                    text = "Continue With",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier.clip(RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(6.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.gooleimage),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(100.dp)
                                )
                                .padding(start = 10.dp, end = 10.dp)

                        )
                        androidx.compose.material3.Text(
                            text = "Google",
                            color = Color.Black,
                            modifier = Modifier.padding(end = 10.dp),
                            textAlign = TextAlign.Center
                        )


                    }
                }



                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    androidx.compose.material3.Text(
                        text = "Already have an account?",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .width(4.dp)
                    )
                    androidx.compose.material3.Text(
                        text = "Login",
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }
        }
    }


}

fun progressFlow(): Flow<Int> = flow {
    var progress = 0
    while (true) {
        emit(progress)
        delay(50) // Emit progress every 50 milliseconds
        progress += 1
        if (progress > 100) progress = 0 // Reset progress after reaching 100
    }
}