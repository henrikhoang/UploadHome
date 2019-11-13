package com.example.uploadhome

sealed class NavigationCenter {
    data class To(val destination : Int) : NavigationCenter()
}