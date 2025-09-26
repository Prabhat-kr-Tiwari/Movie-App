package com.prabhat.movieapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prabhat.movieapp.ui.theme.MovieAppTheme

@Composable
fun SettingsToggleItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = title,
            color = MaterialTheme.colorScheme.onBackground, // or use MaterialTheme.colorScheme.onBackground
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.Red,
                checkedTrackColor = Color.Red.copy(alpha = 0.7f),
                uncheckedTrackColor = Color.White.copy(alpha = 0.4f)
            )
        )
    }
}
@Composable
@ThemeAnnotation
fun SettingsToggleItemPreview() {
    var isChecked by remember { mutableStateOf(false) }

    // Optional: Wrap in a surface or theme for better visual preview
    MovieAppTheme{
        SettingsToggleItem(
            title = "Autoplay",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it }
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

