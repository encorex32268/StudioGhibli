package com.lihan.studioghibli.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.studioghibli.core.presentation.ui.GhibliTheme

@Composable
@Preview
fun App() {
    GhibliTheme {
        NavigationRoot()
    }
}