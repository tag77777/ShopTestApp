package a77777_888.me.t.shoptestapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Pink,
    secondary = LightPink,
    tertiary = Orange
)

private val LightColorScheme = lightColorScheme(
    primary = Pink,
    onPrimary = White,
    primaryContainer = Pink,
    onPrimaryContainer = White,
    inversePrimary = Color.Blue,
    secondary = LightPink,
    onSecondary = White,
    secondaryContainer = Pink,
    onSecondaryContainer = White,
    tertiary = Orange,
    onTertiary = White,
    tertiaryContainer = Orange,
    onTertiaryContainer = White,
    background = White,
    onBackground = DarkGray,
    surface = White,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = Gray,
    surfaceTint = LightPink,
    inverseSurface = DarkGray,
    inverseOnSurface = White,
//    error = Color.Red,
    onError = White,
    errorContainer = Color.Cyan,
    onErrorContainer = White,
    outline = Pink,
    outlineVariant = LightPink,
    scrim = DarkGray,
)


@Composable
fun ShopTestAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme =  LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}

//@Composable
//fun ShopTestAppTheme(
//    darkTheme: Boolean = false, //isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        shapes = Shapes,
//        typography = Typography,
//        content = content
//    )
//}


//@Composable
//fun ShopTestAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        shapes = Shapes,
//        typography = Typography,
//        content = content
//    )
//}