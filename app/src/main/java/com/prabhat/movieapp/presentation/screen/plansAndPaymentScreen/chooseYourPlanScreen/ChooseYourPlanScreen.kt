package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

import android.util.Log
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceEntity
import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import com.prabhat.movieapp.ui.theme.MovieAppTheme


@Composable
fun ChooseYourPlanScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    chooseYourPlanScreenViewModel: ChooseYourPlanScreenViewModel = hiltViewModel()
) {

    val state by chooseYourPlanScreenViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d("PRABHAT", "ChooseYourPlanScreen:   LaunchedEffect(Unit)")
        chooseYourPlanScreenViewModel.navigationFlow.collect { event ->
            Log.d(
                "PRABHAT",
                "ChooseYourPlanScreen:   chooseYourPlanScreenViewModel.navigationFlow.collect"
            )

            when (event) {

                ChoosePlanNavigationEvent.NavigateNext -> {
                    Log.d(
                        "PRABHAT",
                        "ChooseYourPlanScreen:      ChoosePlanNavigationEvent.NavigateNext "
                    )

                    navHostController.navigate(
                        PlansAndPaymentDestination.ChooseYourPaymentModeScreen
                    )
                    chooseYourPlanScreenViewModel.resetNavigationFlag()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier

            .background(MaterialTheme.colorScheme.surface), bottomBar = {

        }
    ) { innerPadding ->
        systemUiController.setStatusBarColor(color = statusBarColor)
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .weight(1f) // Take up remaining space
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
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
                    text = "Choose your Plan",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Text(
                    text = "Cancel at any time",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )



                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 20.dp)
                        .background(MaterialTheme.colorScheme.surface)
                ) {

                    // Movies & Series
                    SelectablePlanButton(
                        title = "Movies & Series",
                        price = "$20/month",
                        isSelected = state.selectedPlan == "movies_series",
                        onClick = {
                            chooseYourPlanScreenViewModel.onEvent(
                                ChoosePlanEvent.PlanSelected("movies_series")
                            )
                        }
                    )

                    // Movies
                    SelectablePlanButton(
                        title = "Movies",
                        price = "$15/month",
                        isSelected = state.selectedPlan == "movies",
                        onClick = {
                            chooseYourPlanScreenViewModel.onEvent(ChoosePlanEvent.PlanSelected("movies"))
                        }
                    )

                    // Series
                    SelectablePlanButton(
                        title = "Series",
                        price = "$15/month",
                        isSelected = state.selectedPlan == "series",
                        onClick = {

                            chooseYourPlanScreenViewModel.onEvent(ChoosePlanEvent.PlanSelected("series"))
                        }
                    )
                }


                //adding continue button


                Spacer(modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .weight(1f))

                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)

                    ) {

                        Button(
                            onClick = {
                                Log.d("PRABHAT", "ChooseYourPlanScreen:onClick ")
                                chooseYourPlanScreenViewModel.onEvent(
                                    ChoosePlanEvent.ContinueClicked
                                )

                            },
                            enabled = state.selectedPlan.isNotEmpty() && !state.isLoading,
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                if (state.isLoading) Color.Transparent else Color.Red
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.isLoading) Color.Red else MaterialTheme.colorScheme.background,
                                disabledContainerColor = MaterialTheme.colorScheme.surface,
                                contentColor = Color.White,
                                disabledContentColor = Color.White
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
                                modifier = Modifier

                                    .fillMaxSize(),
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

                        Spacer(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .height(12.dp)

                        ) // Add space between the button and text

                    }



            }


        }
    }

}

@Composable
fun SelectablePlanButton(
    title: String,
    price: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(
                1.dp,
                if (isSelected) Color.Transparent else Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color.Red else MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 20.dp,
                pressedElevation = 30.dp,
                focusedElevation = 30.dp,
                hoveredElevation = 30.dp
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

class FakeUserPreferenceRepository : UserPreferenceRepository {

    private var storedPreference: UserPreferenceEntity? = null

    override suspend fun savePreference(pref: UserPreferenceEntity) {
        storedPreference = pref
    }

    override suspend fun updateField(field: String, value: String) {

    }

    override suspend fun getPreference(): UserPreferenceEntity? {
        return storedPreference
    }

    override suspend fun clearPreference() {
        storedPreference = null
    }
}

@ThemeAnnotation
@Composable
fun ChooseYourPlanScreenPreview() {

    // Mock NavHostController for the preview
    val navController = rememberNavController()
    val userPreferenceRepository = FakeUserPreferenceRepository()
    val savePreferenceUseCase = SavePreferenceUseCase(userPreferenceRepository)

    val chooseYourPlanScreenViewModel = ChooseYourPlanScreenViewModel(savePreferenceUseCase)

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()
    MovieAppTheme {
// Preview of your screen with a placeholder statusBarColor
        ChooseYourPlanScreen(
            modifier = Modifier,
            navHostController = navController,
            systemUiController = systemUiController,
            statusBarColor = Color.Blue,// or any other color
            chooseYourPlanScreenViewModel = chooseYourPlanScreenViewModel
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
@Preview(
    name = "Landscape Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO,
    widthDp = 800,
    heightDp = 400
)
annotation class ThemeAnnotation
