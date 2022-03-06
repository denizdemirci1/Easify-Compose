package com.dendem.easify.presentation

import com.dendem.easify.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home: BottomNavItem("Home", R.drawable.ic_home, "home")
    object History: BottomNavItem("History", R.drawable.ic_history, "history")
    object Favorites: BottomNavItem("Favorites", R.drawable.ic_favorites, "favorites")
}
