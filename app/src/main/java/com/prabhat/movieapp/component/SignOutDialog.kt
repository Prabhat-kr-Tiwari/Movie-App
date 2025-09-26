package com.prabhat.movieapp.component


import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun SignOutAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,

    ) {

    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon", tint = MaterialTheme.colorScheme.onBackground)
        },
        title = {
            Text(
                text = dialogTitle, color = MaterialTheme.colorScheme.onBackground,
            )
        },
        text = {
            Text(
                text = dialogText, color = MaterialTheme.colorScheme.onBackground,
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    "Yes", color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    "Cancel", color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    )

}

@ThemeAnnotation
@Composable
fun PreviewSignOutDialog(modifier: Modifier = Modifier) {
    val openAlertDialog = remember { mutableStateOf(true) }

    MovieAppTheme {
        when {
            openAlertDialog.value -> {
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
}