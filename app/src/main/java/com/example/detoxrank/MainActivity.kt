package com.example.detoxrank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.DetoxRankTheme
import com.example.detoxrank.ui.DetoxRankApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DetoxRankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    DetoxRankApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetoxRankPreview() {
    DetoxRankTheme {
        DetoxRankApp(windowSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun DetoxRankMediumPreview() {
    DetoxRankTheme {
        DetoxRankApp(windowSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun DetoxRankExpandedPreview() {
    DetoxRankTheme {
        DetoxRankApp(windowSize = WindowWidthSizeClass.Expanded)
    }
}
