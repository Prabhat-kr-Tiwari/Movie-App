/*
package com.example.movieapp.presentation.screen.introScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Text
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import com.google.accompanist.pager.HorizontalPager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.prabhat.movieapp.navigation.Destination
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
fun IntroScreen(modifier: Modifier = Modifier,navHostController: NavHostController) {
    val pagerState = rememberPagerState()
    val list = getList()
    val isButtonVisible = remember {
        derivedStateOf {
            pagerState.currentPage == list.size - 1
        }
    }

    val scope= rememberCoroutineScope()
    val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()

    // Auto-scroll logic
  */
/*  LaunchedEffect(pagerState.currentPage) {

        while (pagerState.currentPage < list.size - 1) {
//            delay(3000) // Delay in milliseconds (3 seconds in this case)
            delay(3000)
            scope.launch {

                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }*//*

    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest {isDragged->
                if (!isDragged) {
                    while (pagerState.currentPage<list.size-1){
                        delay(3000)
                        kotlin.runCatching {
                            pagerState.animateScrollToPage(pagerState.currentPage+1)
                        }
                    }
                }

            }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .background(
                        Color.Black
                    )
                    .padding(bottom = 20.dp)
            ) {
                HorizontalPager(
                    count = list.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    key={list[it].image}
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = list[currentPage].image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )



                        Text(
                            text = list[currentPage].desc,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 60.dp)
                                .align(Alignment.BottomCenter),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
                        )


                    }


                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(bottom = 20.dp), activeColor = Color.Red,
                    inactiveColor = Color.White

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    if (isButtonVisible.value) {
                        ElevatedButton(
                            onClick = { navHostController.navigate(Destination.SignUpScreen)},
                            modifier = Modifier,
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
                            modifier = Modifier,
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

            }


        }


    }


}


*/
