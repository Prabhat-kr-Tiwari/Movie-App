package com.prabhat.movieapp.presentation.screen.more.accountScreen

import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prabhat.movieapp.R
import com.prabhat.movieapp.presentation.components.CenteredAlignTopAppBar
import com.prabhat.movieapp.presentation.components.CustomContainedLoadingIndicator
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun AccountScreen(
    innerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onNavigateUp:()->Unit,
    accountScreenViewModel: AccountScreenViewModel= hiltViewModel()
    ) {
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
    val accountScreenUiState = accountScreenViewModel.accountScreenUiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        .padding(innerPaddingValues)
        .background(MaterialTheme.colorScheme.surface), topBar = {

        CenteredAlignTopAppBar(title = "Account", topAppBarScrollBehavior = scrollBehavior) {
            onNavigateUp()
        }


    }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(innerPadding),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Who's  Watching?",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp, textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            when(val state = accountScreenUiState.value){
                AccountScreenUiState.LoadFailed -> {
                    Text(
                        text = "Failed to load user",
                        modifier = Modifier.padding(40.dp),
                        color = MaterialTheme.colorScheme.error
                    )

                }
                AccountScreenUiState.Loading -> {
                    CustomContainedLoadingIndicator()

                }
                AccountScreenUiState.NotShown -> {
                    Text(
                        text = "No user data",
                        modifier = Modifier.padding(40.dp)
                    )
                }
                is AccountScreenUiState.UserPreference -> {
                    val avatarIndex = state.avatarId.toInt()
                    val painterResource = imageResources[avatarIndex]
                    GlowingCircleWithImage(painterResource)
                    Text(
                        text = state.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp, textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    )
                }
            }

            LazyRow {
                item {
                    AccountItem(
                        "MRPANTHER",
                        drawable = ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.ellipse13
                        )!!
                    )
                    AccountItem(
                        "MRPANTHER",
                        drawable = ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.ellipse14
                        )!!
                    )


                }
                item {


                }
            }


            Box(modifier = Modifier.clip(CircleShape)
                .size(100.dp).background(color = Color.Gray), contentAlignment = Alignment.Center){


                Icon(imageVector =Icons.Default.Add, contentDescription = "add",
                    modifier = Modifier.fillMaxSize()
                    .graphicsLayer(
                        scaleX = 0.7f, // Shrink width by 50%
                        scaleY = 0.7f
                    ))

            }
            Text(
                text = "Create Profile",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp, textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
        }


    }
}

@Composable
fun AccountItem(accountName: String, drawable: Drawable, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        , modifier = modifier.padding(vertical = 20.dp,horizontal = 20.dp)
    ) {
        Image(
            bitmap = drawable.toBitmap().asImageBitmap(),
            contentDescription = "Spiderman",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = accountName,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp, textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
        )

    }

}

@Composable
fun GlowingCircleWithImage(painterResource: Painter) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        // Draw concentric glowing circles
        Canvas(modifier = Modifier.size(300.dp)) {
            val radius = size.minDimension / 2
            for (i in 5 downTo 1) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Red.copy(alpha = 0.2f * i),
                            Color.Transparent
                        ),
                        radius = radius * (i / 5f)
                    ),
                    radius = radius * (i / 5f),
                    center = center
                )
            }
        }

        // Load an image in the center
        Image(
            painter = painterResource, // Replace with your drawable or rememberAsyncImagePainter
            contentDescription = "Spiderman",
            modifier = Modifier.size(160.dp)
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTopBar(){
    MovieAppTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        AccountTopBar(scrollBehavior) {

        }
    }
}
@Composable
fun AccountTopBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onNavigateUp: () -> Unit
) {

    CenterAlignedTopAppBar(
       modifier = Modifier
        ,
        scrollBehavior=topAppBarScrollBehavior,

        title = {
            Box(modifier=Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Account",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,

                    )
            }
        },
        navigationIcon = {
                IconButton(
                    modifier = Modifier.size(40.dp)
                       ,
                    onClick = { onNavigateUp() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "back"
                    )
                }


        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}
//@ThemeAnnotation
@Composable
fun PreviewAccountScreen() {

    MovieAppTheme {
        AccountScreen(onNavigateUp={}, innerPaddingValues = PaddingValues())
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