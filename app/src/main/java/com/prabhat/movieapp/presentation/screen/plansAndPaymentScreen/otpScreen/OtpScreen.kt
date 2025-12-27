package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.otpScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen.BillingDetailsScreen
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(modifier: Modifier = Modifier, navHostController: NavHostController, systemUiController: SystemUiController, statusBarColor: Color) {
    systemUiController.setStatusBarColor(color = statusBarColor)

    Scaffold(
        modifier = modifier

            .background(MaterialTheme.colorScheme.surface)
    ) { innerPadding ->
        Column(
            modifier = modifier   .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
             , verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



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
                    text = "Enter your OTP",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier
                    .height(10.dp)
                    .background(color = MaterialTheme.colorScheme.surface))
                Text(
                    text = "Please enter the OTP that we've sent on  your phone number 55XXXXXXX89 linked with your bank account",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(20.dp)
                )



            var isClicked by remember { mutableStateOf(false) } // State to track button click

      /*      Row(
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
                            .background(  if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainer
                            })
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

                                    if (otp1State.text.isEmpty()) {
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
                                if (otp1State.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            otp1State.edit {
                                                replace(0, otp1State.text.length, "")
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


                    val otp2IdState = rememberTextFieldState()

                    BasicTextField(
                        state = otp2IdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(  if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainer
                            })
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

                                    if (otp2IdState.text.isEmpty()) {
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
                                if (otp2IdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            otp2IdState.edit {
                                                replace(0, otp2IdState.text.length, "")
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


                    val otp3IdState = rememberTextFieldState()

                    BasicTextField(
                        state = otp3IdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(  if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainer
                            })
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

                                    if (otp3IdState.text.isEmpty()) {
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
                                if (otp3IdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            otp3IdState.edit {
                                                replace(0, otp3IdState.text.length, "")
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


                    val otp4IdState = rememberTextFieldState()

                    BasicTextField(
                        state = otp4IdState,
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(  if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainer
                            })
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

                                    if (otp4IdState.text.isEmpty()) {
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
                                if (otp4IdState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            otp4IdState.edit {
                                                replace(0, otp4IdState.text.length, "")
                                            }
                                        })
                                }


                            }

                        }

                    )
                }




            }*/
            OtpInputRow()



            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {

                Text(text = "Didn't receive the OTP?", color = Color.Red)

            }
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                var isContinueClicked by remember {mutableStateOf(false)}

                LaunchedEffect(isContinueClicked) {
                    if(isContinueClicked){
                        navHostController.navigate(PlansAndPaymentDestination.VerifyPaymentScreen)
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
                                isContinueClicked = !isContinueClicked // Toggle the clicked state

                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, if (isContinueClicked) Color.Transparent else Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isContinueClicked) Color.Red else MaterialTheme.colorScheme.surface,
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
                                modifier = Modifier.fillMaxSize(),
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


                    }
                }
            }


        }
    }

}
@ThemeAnnotation
@Composable
fun OtpScreenPreview() {
    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    MovieAppTheme{
        // Preview of your screen with a placeholder statusBarColor
        OtpScreen(
            modifier = Modifier,
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Blue // or any other color
        )
    }

}
/*@Composable
fun OtpTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    previousFocusRequester: FocusRequester?,
    onBackspace: () -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.text.length <= 1 && it.text.all { ch -> ch.isDigit() }) {
                onValueChange(it)
                if (it.text.isNotEmpty()&&value.text.isEmpty()) {
                    nextFocusRequester?.requestFocus()
                }
            }
        },
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 1.dp,
                color = if (value.text.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace) {
                    if (value.text.isNotEmpty()) {
                        // Clear current field
                        onValueChange(TextFieldValue(""))
                    } else {
                        // Field is empty, handle backspace navigation
                        previousFocusRequester?.requestFocus()
                        onBackspace()
                    }
                    true
                } else {
                    false
                }
            }
           ,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        ),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                innerTextField()
            }
        }
    )
}
@Preview
@Composable
fun OtpInputRow() {
    val otpValues = remember { List(4) { mutableStateOf(TextFieldValue("")) } }
    val focusRequesters = remember { List(4) { FocusRequester() } }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    ) {
        otpValues.forEachIndexed { index, state ->
            val next = focusRequesters.getOrNull(index + 1)
            val previous = focusRequesters.getOrNull(index - 1)

            OtpTextField(
                value = state.value,
                onValueChange = { state.value = it },
                focusRequester = focusRequesters[index],
                nextFocusRequester = next,
                previousFocusRequester = previous,

                onBackspace = {
                   *//* if (previous != null && index > 0) {
                        otpValues[index - 1].value = TextFieldValue("") // optionally clear previous box
                        previous.requestFocus()
                    }*//*
                    if (index > 0) {
                        // Clear the previous field and move focus to it
                        otpValues[index - 1].value = TextFieldValue("")
                        // Use LaunchedEffect to ensure focus change happens after state update
                        kotlinx.coroutines.GlobalScope.launch {
                            kotlinx.coroutines.delay(10) // Small delay to ensure state is updated
                            focusRequesters[index - 1].requestFocus()
                        }
                    }

                }
            )
        }
    }
}*/

@Composable
fun OtpTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    previousFocusRequester: FocusRequester?,
    onBackspace: () -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.text.length <= 1 && it.text.all { ch -> ch.isDigit() }) {
                onValueChange(it)
                if (it.text.isNotEmpty() && value.text.isEmpty()) {
                    nextFocusRequester?.requestFocus()
                }
            }
        },
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 1.dp,
                color = if (value.text.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            .onKeyEvent { event ->
                if(event.key == Key.Backspace){

                    Log.d("BRAVO", "OtpTextField: Backspace")
                }
                if (event.type == KeyEventType.KeyDown || event.key == Key.Backspace) {
                    Log.d(
                        "BRAVO",
                        "OtpTextField: event.type == KeyEventType.KeyDown && event.key == Key.Backspace"
                    )
                    if (value.text.isNotEmpty()) {
                        Log.d("BRAVO", "OtpTextField: value.text.isNotEmpty()")
                        // Clear current field
                        onValueChange(TextFieldValue(""))
                    } else {
                        Log.d("BRAVO", "OtpTextField: onback space")
                        // Field is empty, handle backspace navigation
                        onBackspace()
                    }
                    true
                } else {
                    false
                }
            },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        ),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun OtpInputRow() {
    val otpValues = remember { List(4) { mutableStateOf(TextFieldValue("")) } }
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        otpValues.forEachIndexed { index, state ->
            val next = focusRequesters.getOrNull(index + 1)
            val previous = focusRequesters.getOrNull(index - 1)

            OtpTextField(
                value = state.value,
                onValueChange = { state.value = it },
                focusRequester = focusRequesters[index],
                nextFocusRequester = next,
                previousFocusRequester = previous,
                onBackspace = {
                    if (index > 0) {
                        Log.d("BRAVO", "OtpInputRow: "+index)
                        // Use LaunchedEffect approach for better timing control
                        coroutineScope.launch {
                            // First, request focus to previous field
                            focusRequesters[index - 1].requestFocus()
                            // Then wait a frame and clear it
                            kotlinx.coroutines.delay(16) // One frame at 60fps
                            otpValues[index - 1].value = TextFieldValue("")
                        }
                    }
                }
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
