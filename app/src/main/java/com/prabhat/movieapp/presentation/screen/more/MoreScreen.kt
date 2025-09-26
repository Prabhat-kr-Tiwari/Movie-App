package com.prabhat.movieapp.presentation.screen.more

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prabhat.movieapp.R
import com.prabhat.movieapp.component.SignOutAlertDialog
import com.prabhat.movieapp.presentation.screen.home.movieDetail.verticalPadding
import com.prabhat.movieapp.ui.theme.MovieAppTheme


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoreScreen(innerPaddingValues: PaddingValues,modifier: Modifier = Modifier,onOpenAccount:()->Unit,onOpenSettings:()->Unit) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(innerPaddingValues)
            .fillMaxSize()
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                .size(160.dp)
                .clip(RectangleShape),
            contentAlignment = Alignment.Center

        ) {
            MatrixGrid(size = 26, borderColor = Color.Red, modifier = Modifier.size(200.dp))

            Image(
                painter = painterResource(id = R.drawable.ellipse13),
                modifier = Modifier
                    .size(140.dp)
                    .border(
                        border = (BorderStroke(
                            1.dp, color = Color.Red
                        )), shape = CircleShape
                    ),
                contentDescription = "Description of your image" // Important for accessibility
            )
        }
        Text(
            text = "UIUXDIVYANSHU",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground, fontWeight = Bold,
            modifier = Modifier.padding(16.dp)
        )


        val moreItemList = listOf<String>(
            "Account",
            "Settings",
            "Legal",
            "Support",
            "Privacy Settings",
            "Parental Control"
        )

        /*LazyColumn(Modifier.padding(horizontal = 10.dp)) {
            itemsIndexed(moreItemList) { index, moreItem ->
                Spacer(modifier = Modifier.height(10.dp))
                MoreItem(itemName = moreItem, onClick = {
                    when(moreItem){
                        "Account"->onOpenAccount()
                        "Settings"->onOpenSettings()
                    }
                })
            }
        }*/
        moreItemList.forEachIndexed { index, moreItem ->
            MoreItem(itemName = moreItem, onClick = {
                when (moreItem) {
                    "Account" -> onOpenAccount()
                    "Settings" -> onOpenSettings()
                }
            })
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding( horizontal = 20.dp)
                .height(0.5.dp)
                .background(color = MaterialTheme.colorScheme.onBackground)
        )
        val openAlertDialog = remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
                .clickable {
                    openAlertDialog.value = true
                }) {
            Text(
                text = "Sign Out",
                fontWeight = SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 20.dp).clickable{

                },
                textAlign = TextAlign.Start

            )
        }
        if (openAlertDialog.value){
            SignOutAlertDialog(
                onDismissRequest = {
                    openAlertDialog.value = false

                },
                onConfirmation = {
                    openAlertDialog.value = false

                },
                dialogTitle = "Are you sure want to signout",
                dialogText = "",
                icon = Icons.Default.Info


            )
        }


    }
}

@ThemeAnnotation
@Composable
fun previewMoreItem(modifier: Modifier = Modifier) {
    val list = listOf<String>("Account")
    MovieAppTheme {

        MoreItem(itemName = "Account", onClick = {})
    }
}

@Composable
fun MoreItem(modifier: Modifier = Modifier, itemName: String,onClick:()->Unit) {
    Row(
        Modifier.padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable{onClick()}
        , verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(

            text = itemName,
            fontWeight = SemiBold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f)

        )
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.arrow_right else R.drawable.arrow_right_black),
            contentDescription = "arrow right",
            modifier = Modifier.padding(horizontal = 20.dp)
                .background(MaterialTheme.colorScheme.surface)


        )

    }
}


@Composable
fun MatrixGrid(size: Int, borderColor: Color, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
//            .border(0.1.dp, borderColor) // Add a colored border
    ) {
        val cellSize = this.size.minDimension / size // Ensure square cells
        val lineWidth = 0.1.dp.toPx()

        // Draw vertical lines
        for (i in 1 until size) { // Start from 1 to avoid line on the border
            val x = i * cellSize
            drawLine(
                start = Offset(x = x, y = 0f),
                end = Offset(x = x, y = this.size.height),
                color = Color.Red,
                strokeWidth = lineWidth,
                cap = StrokeCap.Square
            )
        }

        // Draw horizontal lines
        for (i in 1 until size) { // Start from 1 to avoid line on the border
            val y = i * cellSize
            drawLine(
                start = Offset(x = 0f, y = y),
                end = Offset(x = this.size.width, y = y),
                color = Color.Red,
                strokeWidth = lineWidth,
                cap = StrokeCap.Square
            )
        }
    }
}

// Example usage:
@Preview
@Composable
fun ExampleMatrix() {
    MatrixGrid(size = 26, borderColor = Color.Red, modifier = Modifier.size(200.dp))
}

@ThemeAnnotation
@Composable
fun previewMoreScreen(modifier: Modifier = Modifier) {
    MovieAppTheme {

        MoreScreen(onOpenAccount = {}, onOpenSettings = {}, innerPaddingValues = PaddingValues())
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

