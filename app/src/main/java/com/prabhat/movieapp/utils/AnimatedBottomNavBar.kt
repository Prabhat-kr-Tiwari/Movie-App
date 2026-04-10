package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────
// Data model
// ─────────────────────────────────────────────────────────────

data class NavItem(
    val label: String,
    val icon: ImageVector,
)

// ─────────────────────────────────────────────────────────────
// Colors (match the dark purple theme in the video)
// ─────────────────────────────────────────────────────────────

private val NavBarBackground   = Color(0xFF1E1428)
private val NavBarBorder       = Color(0xFF6A3FA0)
private val ActivePillBg       = Color(0xFF2A1F3D)
private val ActivePillBorder   = Color(0xFF8B5CF6)
private val ActiveIconColor    = Color(0xFFFFFFFF)
private val InactiveIconColor  = Color(0xFF7B6F8A)

// ─────────────────────────────────────────────────────────────
// Main composable
// ─────────────────────────────────────────────────────────────

@Composable
fun AnimatedBottomNavBar(
    items: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Outer pill container
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(NavBarBackground)
                .border(
                    width = 1.dp,
                    color = NavBarBorder,
                    shape = RoundedCornerShape(50),
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            items.forEachIndexed { index, item ->
                NavBarItem(
                    item = item,
                    isSelected = index == selectedIndex,
                    onClick = { onItemSelected(index) },
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Individual nav item
// ─────────────────────────────────────────────────────────────

@Composable
private fun NavBarItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (isSelected) {
                    Modifier
                        .background(ActivePillBg)
                        .border(
                            width = 1.dp,
                            color = ActivePillBorder,
                            shape = RoundedCornerShape(50),
                        )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (isSelected) ActiveIconColor else InactiveIconColor,
                modifier = Modifier.size(22.dp),
            )

            // Label only visible for selected item
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.label,
                        color = ActiveIconColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Preview / Usage example
// ─────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFF120D1E)
@Composable
fun AnimatedBottomNavBarPreview() {
    // NOTE: Replace Icons.Filled.Star with a cocktail/drinks icon
    // from your icon set (e.g., a custom vector or an icon library).
    val items = listOf(
        NavItem("Drinks",   Icons.Filled.Star),       // swap for a drinks icon
        NavItem("Saved",    Icons.Filled.Star),
        NavItem("Nearby",   Icons.Filled.LocationOn),
        NavItem("Profile",  Icons.Filled.Person),
    )

    var selected by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF120D1E)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AnimatedBottomNavBar(
            items = items,
            selectedIndex = selected,
            onItemSelected = { selected = it },
        )
    }
}

// ─────────────────────────────────────────────────────────────
// How to use inside a Scaffold
// ─────────────────────────────────────────────────────────────
//
// val navItems = listOf(
//     NavItem("Drinks",  Icons.Filled.LocalBar),
//     NavItem("Saved",   Icons.Filled.Star),
//     NavItem("Nearby",  Icons.Filled.LocationOn),
//     NavItem("Profile", Icons.Filled.Person),
// )
// var selectedTab by remember { mutableIntStateOf(0) }
//
// Scaffold(
//     bottomBar = {
//         AnimatedBottomNavBar(
//             items = navItems,
//             selectedIndex = selectedTab,
//             onItemSelected = { selectedTab = it },
//         )
//     }
// ) { innerPadding ->
//     // your screen content
// }
