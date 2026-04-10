package com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
import com.prabhat.movieapp.navigation.ProfileDestination
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreatePinScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    createPinScreenViewModel: CreatePinScreenViewModel = hiltViewModel(),
    innerPaddingValues: PaddingValues
) {
    val state = createPinScreenViewModel.uiState.collectAsStateWithLifecycle()
    var isOtpComplete by rememberSaveable { mutableStateOf(false) }

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
    val avatarIndex = state.value.avatarId?.toInt()
    val painterResource = imageResources[avatarIndex ?: 0]
    LaunchedEffect(Unit) {


        createPinScreenViewModel.navigationChannel.collect { event ->
            when (event) {
                CreatePinNavigationEvent.navigateNext -> {
                    navHostController.navigate(ProfileDestination.ProfileCompleteScreen)
                }
            }
        }
    }
    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(innerPaddingValues)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()

        ) {


            Column(
                modifier = modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .height(10.dp)
                )

                Column(
                    modifier = Modifier,
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
                                painter =painterResource,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(140.dp)
                            )


                        }


                    }



                    var otpValue by remember {
                        mutableStateOf("")
                    }

                    /*OtpTextField(
                        otpText = otpValue,
                        onOtpTextChange = { value, otpInputFilled ->
                            otpValue = value
                        }
                    )*/
                    OtpInputRow(
                        onOtpChange = {otp->
                            createPinScreenViewModel.onEvent(CreatePinEvent.OnOtpChange(otp))
                        },
                        onOtpComplete = {isComplete->
                            if (isComplete){
                                isOtpComplete=isComplete


                            }

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



            }
            //here
            Button(
                onClick = {
                   createPinScreenViewModel.onEvent(createPinEvent = CreatePinEvent.iamAllSafeClicked)

                },
                enabled = isOtpComplete,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    1.dp,
                     Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,          // use theme’s onSurface
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
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

@Composable
fun OtpInputRow(onOtpChange:(String)->Unit, onOtpComplete: (Boolean) -> Unit) {
    val otpValues = rememberSaveable { List(4) { mutableStateOf(TextFieldValue("")) } }
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val coroutineScope = rememberCoroutineScope()

    val otpString by remember{
        derivedStateOf {
            otpValues.joinToString(separator = "") { it.value.text }
        }
    }
    LaunchedEffect(otpString) {
        onOtpChange(otpString)
    }
    val isOtpComplete by remember {
        derivedStateOf {
            otpValues.all { it.value.text.length == 1 }
        }
    }
    LaunchedEffect(isOtpComplete) {
        onOtpComplete(isOtpComplete)

    }
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
                        // Use LaunchedEffect approach for better timing control
                        coroutineScope.launch {
                            // First, request focus to previous field
                            focusRequesters[index - 1].requestFocus()
                            // Then wait a frame and clear it
                            delay(16) // One frame at 60fps
                            otpValues[index - 1].value = TextFieldValue("")
                        }
                    }
                }
            )
        }
    }
}
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
                if (event.key == Key.Backspace) {

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
            statusBarColor = Color.Red,
            innerPaddingValues = PaddingValues(20.dp)
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