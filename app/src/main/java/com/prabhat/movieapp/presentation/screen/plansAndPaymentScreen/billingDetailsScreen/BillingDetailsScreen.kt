package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
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
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun BillingDetailsScreen(
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
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)

                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.SpaceEvenly,
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
                        .background(Color.Red)
                        .height(4.dp)
                )

                Box(
                    modifier = Modifier

                        .padding(start = 5.dp, end = 5.dp)
                        .clip(CircleShape)
                        .background(Color.Red)

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
                        .background(Color.Red)
                        .height(4.dp)
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                )


                Box(
                    modifier = Modifier

                        .padding(start = 5.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
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
                text = "Fill your credit /Debit Card Details",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )



            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .background(MaterialTheme.colorScheme.surface)


            ) {

                val firstNameState = rememberTextFieldState()

                BasicTextField(
                    state = firstNameState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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

                                if (firstNameState.text.isEmpty()) {
                                    androidx.compose.material3.Text(
                                        text = "FirstName",
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.background.copy(0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        }
                                    )
                                }
                                innerTextField()
                            }
                            if (firstNameState.text.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        firstNameState.edit {
                                            replace(0, firstNameState.text.length, "")
                                        }
                                    })
                            }


                        }

                    }

                )
                Spacer(modifier = Modifier.height(20.dp))


                val lastNameState = rememberTextFieldState()

                BasicTextField(
                    state = lastNameState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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

                                if (firstNameState.text.isEmpty()) {
                                    androidx.compose.material3.Text(
                                        text = "LastName",
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.background.copy(0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        }
                                    )
                                }
                                innerTextField()
                            }
                            if (lastNameState.text.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        lastNameState.edit {
                                            replace(0, lastNameState.text.length, "")
                                        }
                                    })
                            }


                        }

                    }

                )


                Spacer(modifier = Modifier.height(20.dp))
                //


                val cardNumberState = rememberTextFieldState()

                BasicTextField(
                    state = cardNumberState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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

                                if (firstNameState.text.isEmpty()) {
                                    Text(
                                        text = "CardNumber",
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.background.copy(0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        }
                                    )
                                }
                                innerTextField()
                            }
                            if (cardNumberState.text.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        cardNumberState.edit {
                                            replace(0, cardNumberState.text.length, "")
                                        }
                                    })
                            }


                        }

                    }

                )

                Spacer(modifier = Modifier.height(20.dp))

                //
                val expirationDateState = rememberTextFieldState()

                BasicTextField(
                    state = cardNumberState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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

                                if (expirationDateState.text.isEmpty()) {
                                    Text(
                                        text = "ExpirationDate(MM/YY) ",
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.background.copy(0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        }
                                    )
                                }
                                innerTextField()
                            }
                            if (expirationDateState.text.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        expirationDateState.edit {
                                            replace(0, expirationDateState.text.length, "")
                                        }
                                    })
                            }


                        }

                    }

                )

                Spacer(modifier = Modifier.height(20.dp))


                //
                val securityCodeState = rememberTextFieldState()

                BasicTextField(
                    state = cardNumberState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            if (isSystemInDarkTheme()){
                                MaterialTheme.colorScheme.onBackground
                            }else{
                                MaterialTheme.colorScheme.surfaceContainerHighest
                            }

                        )
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

                                if (securityCodeState.text.isEmpty()) {
                                    Text(
                                        text = "ExpirationDate(MM/YY) ",
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.background.copy(0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        }
                                    )
                                }
                                innerTextField()
                            }
                            if (securityCodeState.text.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        securityCodeState.edit {
                                            replace(0, securityCodeState.text.length, "")
                                        }
                                    })
                            }


                        }

                    }

                )

            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Movies & Series $20/month", color = MaterialTheme.colorScheme.onBackground)
                Text(text = "Change", color = Color.Red)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                var isContinueClicked by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(isContinueClicked) {
                    if (isContinueClicked) {
                        navHostController.navigate(PlansAndPaymentDestination.OtpScreen)
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
                            border = BorderStroke(
                                1.dp,
                                if (isContinueClicked) Color.Transparent else Color.Red
                            ),
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
fun BillingDetailsScreenPreview() {
    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    MovieAppTheme {
        // Preview of your screen with a placeholder statusBarColor
        BillingDetailsScreen(
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
