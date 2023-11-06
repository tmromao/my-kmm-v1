package presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey


data class ScreenHome(
    val greetingText: String,
    val showImage: Boolean,
): Screen {

    override val key: ScreenKey
        get()= uniqueScreenKey

    @Composable
    override fun Content() {
        Text("Hello, World!")
    }
}