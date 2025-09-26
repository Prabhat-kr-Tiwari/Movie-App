package com.prabhat.movieapp.presentation.screen.introScreen


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.repository.AuthenticationRepositoryImpl
import com.prabhat.movieapp.domain.repository.AuthenticationRepository
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import com.prabhat.movieapp.navigation.Destination
import com.prabhat.movieapp.navigation.PlansAndPaymentDestination
import com.prabhat.movieapp.presentation.screen.more.accountScreen.ThemeAnnotation
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


data class HorizontalPagerContent(
    @DrawableRes val image: Int,
    val desc: String
)

fun getList(): List<HorizontalPagerContent> {
    return listOf(
        HorizontalPagerContent(

            R.drawable.avenger_image,
            "All your favourite MARVEL Movies and Series at one Place"
        ),
        HorizontalPagerContent(

            R.drawable.deadpool_image,
            "Watch Online or Download offline"
        ),
        HorizontalPagerContent(

            R.drawable.circle_man_image,
            "Create Profiles for different memeber and get personalised recommendation"
        ),
        HorizontalPagerContent(

            R.drawable.iron_man_image,
            "Plans according to your needs and at affordable price"
        ),
        HorizontalPagerContent(

            R.drawable.thor_image,
            "Lets get Started"
        ),
        HorizontalPagerContent(

            R.drawable.captain_america_image,
            "Plans according to your needs and at affordable price"
        )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    introScreenViewModel: IntroScreenViewModel = hiltViewModel()
) {
    val list = getList()
    val introScreenState = introScreenViewModel.sessionIdState.value
    val pagerState = rememberPagerState(pageCount = { list.size })
    val isButtonVisible = remember {
        derivedStateOf {
            pagerState.currentPage == list.size - 1
        }
    }
    val lastPageOfViewPager = remember {
        derivedStateOf { pagerState.currentPage == list.size - 1 }
    }
    if (lastPageOfViewPager.value) {

        if (introScreenState.sessionId?.sessionId?.isNotEmpty() == true) {

            LaunchedEffect(Unit) {
                navHostController.navigate(PlansAndPaymentDestination.ChooseYourPlanScreen)
            }

        }
    }

    val coroutineScope = rememberCoroutineScope()
    val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()


    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (!isDragged) {
                    while (pagerState.currentPage < list.size - 1) {

                        delay(3000)
                        kotlin.runCatching {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }

            }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        HorizontalPager(

            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            key = { list[it].image },
            pageSize = PageSize.Fill

        ) { index ->


            Image(
                painter = painterResource(id = list[index].image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!isButtonVisible.value) {
                Text(
                    text = list[pagerState.currentPage].desc,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 60.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                if (isButtonVisible.value) {
                    ElevatedButton(
                        onClick = { navHostController.navigate(Destination.SignUpScreen) },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(horizontal = 10.dp),
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            disabledContainerColor = Color.White,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 20.dp,
                            pressedElevation = 30.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp,
                            disabledElevation = 0.dp
                        ), border = BorderStroke(1.dp, Color.White)
                    ) {

                        Box(modifier = Modifier) {


                            Text(
                                text = "SignUp",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    }

                    ElevatedButton(
                        onClick = { navHostController.navigate(Destination.LoginScreen) },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(horizontal = 10.dp),
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            disabledContainerColor = Color.White,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 20.dp,
                            pressedElevation = 30.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp,
                            disabledElevation = 0.dp
                        ), border = BorderStroke(1.dp, Color.White)
                    ) {

                        Text(
                            text = "Login",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .offset(y = -(16).dp)
                    .fillMaxWidth(0.5f)
                    .clip(
                        RoundedCornerShape(100.dp)
                    )
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(8.dp)

            ) {
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }


            }
        }


    }

}

//@ThemeAnnotation
//@Composable
//fun previewIntroScreen(modifier: Modifier = Modifier) {
//
//
//    val navHostController = rememberNavController()
//    val authenticationRepository = AuthenticationRepositoryImpl()
//    val authenticationUseCase = AuthenticationUseCase(authenticationRepository)
//    val introScreenViewModel = IntroScreenViewModel(authenticationUseCase)
//    MovieAppTheme {
//        IntroScreen(
//            modifier = Modifier,
//            navHostController = navHostController,
//            introScreenViewModel = introScreenViewModel,
//
//        )
//    }
//}

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





