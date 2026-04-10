package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.PlanType
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BillingDetailsScreen(
    innerPaddingValues: PaddingValues,
    selectedPlanType: PlanType,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    billingDetailsScreenViewModel: BillingDetailsScreenViewModel = hiltViewModel()
) {
    val state by billingDetailsScreenViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        billingDetailsScreenViewModel.navigationChannel.collect { event ->
            when (event) {
                BillingDetailsNavigationEvent.NavigateBack -> {
                    navHostController.popBackStack()

                }

                BillingDetailsNavigationEvent.NavigateNext -> {
                    navHostController.navigate(PlansAndPaymentDestination.OtpScreen)

                }
            }

        }
    }
    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(innerPaddingValues)
            .consumeWindowInsets(innerPaddingValues)



    ) { innerPadding ->
        systemUiController.setStatusBarColor(color = statusBarColor)
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
                .imeNestedScroll()
        ) {


            Column(
                modifier = modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())

                  ,
                verticalArrangement = Arrangement.spacedBy(20.dp),
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
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(top = 10.dp)


                ) {


                    BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                        state = billingDetailsScreenViewModel.firstNameState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                }
                            )
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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

                                    if (billingDetailsScreenViewModel.firstNameState.text.isEmpty()) {
                                       Text(
                                            text = "First name",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (billingDetailsScreenViewModel.firstNameState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            billingDetailsScreenViewModel.firstNameState.edit {
                                                replace(
                                                    0,
                                                    billingDetailsScreenViewModel.firstNameState.text.length,
                                                    ""
                                                )
                                            }
                                        })
                                }


                            }

                        }

                    )
                    Spacer(modifier = Modifier.height(20.dp))



                    BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                        state = billingDetailsScreenViewModel.lastNameState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                }
                            )
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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

                                    if (billingDetailsScreenViewModel.lastNameState.text.isEmpty()) {
                                       Text(
                                            text = "Last name",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (billingDetailsScreenViewModel.lastNameState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            billingDetailsScreenViewModel.lastNameState.edit {
                                                replace(
                                                    0,
                                                    billingDetailsScreenViewModel.lastNameState.text.length,
                                                    ""
                                                )
                                            }
                                        })
                                }


                            }

                        }

                    )


                    Spacer(modifier = Modifier.height(20.dp))
                    //


                    BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                        state = billingDetailsScreenViewModel.cardNumberState,
                        inputTransformation = InputTransformation.maxLength(16).then {
                            if (!asCharSequence().isDigitsOnly()) {
                                revertAllChanges()
                            }
                        },
                        outputTransformation = OutputTransformation {
                            var i = 4
                            while (i < length) {
                                insert(i, " ")
                                i += 5 // move ahead considering inserted space
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                }
                            )
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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
                                        imageVector = Icons.Rounded.CreditCard,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        contentDescription = null

                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {

                                    if (billingDetailsScreenViewModel.cardNumberState.text.isEmpty()) {
                                        Text(
                                            text = "Card number",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (billingDetailsScreenViewModel.cardNumberState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            billingDetailsScreenViewModel.cardNumberState.edit {
                                                replace(
                                                    0,
                                                    billingDetailsScreenViewModel.cardNumberState.text.length,
                                                    ""
                                                )
                                            }
                                        })
                                }


                            }

                        }

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    //

                    BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        state = billingDetailsScreenViewModel.expirationDateState,
                        inputTransformation = InputTransformation.maxLength(4).then {
                            if (!asCharSequence().isDigitsOnly()) {
                                revertAllChanges()
                            }
                        },
                        outputTransformation = OutputTransformation {
                            if (length > 0) insert(0, "(")
                            if (length > 2) insert(3, "/")
                            if (length > 5) insert(6, ")")

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                }
                            )
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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
                                        imageVector = Icons.Rounded.DateRange,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        contentDescription = null

                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {

                                    if (billingDetailsScreenViewModel.expirationDateState.text.isEmpty()) {
                                        Text(
                                            text = "ExpirationDate (MM/YY) ",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (billingDetailsScreenViewModel.expirationDateState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            billingDetailsScreenViewModel.expirationDateState.edit {
                                                replace(
                                                    0,
                                                    billingDetailsScreenViewModel.expirationDateState.text.length,
                                                    ""
                                                )
                                            }
                                        })
                                }


                            }

                        }

                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    //

                    BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),

                        state = billingDetailsScreenViewModel.securityCodeState,
                        inputTransformation = InputTransformation.maxLength(3).then {
                            if (!asCharSequence().isDigitsOnly()) {
                                revertAllChanges()
                            }
                        },
                        outputTransformation = OutputTransformation {
                            if (length >= 1) insert(0, "(")
                            if (length > 3) insert(4, ")")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isSystemInDarkTheme()) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                }

                            )
                            .padding(10.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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
                                        imageVector = Icons.Rounded.Info,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        contentDescription = null

                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {

                                    if (billingDetailsScreenViewModel.securityCodeState.text.isEmpty()) {
                                        Text(
                                            text = "Security Code (CVV) ",
                                            color = if (isSystemInDarkTheme()) {
                                                MaterialTheme.colorScheme.background.copy(0.5f)
                                            } else {
                                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                            }
                                        )
                                    }
                                    innerTextField()
                                }
                                if (billingDetailsScreenViewModel.securityCodeState.text.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            billingDetailsScreenViewModel.securityCodeState.edit {
                                                replace(
                                                    0,
                                                    billingDetailsScreenViewModel.securityCodeState.text.length,
                                                    ""
                                                )
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
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                Text(text = "Movies & Series $20/month", color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        text = "${selectedPlanType.displayName} ${selectedPlanType.price}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(text = "Change", color = Color.Red, modifier = modifier.clickable {
                        billingDetailsScreenViewModel.onEvent(
                            BillingDetailsEvent.ChangeClicked
                        )
                    })
                }



            }
            Button(
                onClick = {
                    billingDetailsScreenViewModel.onEvent(
                        BillingDetailsEvent.ContinueClicked
                    )

                },
                enabled = billingDetailsScreenViewModel.firstNameState.text.isNotEmpty()
                        && billingDetailsScreenViewModel.lastNameState.text.isNotEmpty()
                        && billingDetailsScreenViewModel.cardNumberState.text.isNotEmpty()
                        && billingDetailsScreenViewModel.expirationDateState.text.isNotEmpty()
                        && billingDetailsScreenViewModel.securityCodeState.text.isNotEmpty()
                        && !state.isLoading,

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
            innerPaddingValues = PaddingValues(20.dp),
            selectedPlanType = PlanType.MovieSeries,
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
