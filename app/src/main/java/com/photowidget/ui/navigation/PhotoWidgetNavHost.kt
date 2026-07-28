package com.photowidget.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.photowidget.data.WidgetConfig
import com.photowidget.ui.components.overlay.ResetWidgetDialog
import com.photowidget.ui.screens.about.AboutScreen
import com.photowidget.ui.screens.appsettings.AppSettingsScreen
import com.photowidget.ui.screens.appsettings.LanguageScreen
import com.photowidget.ui.screens.home.HomeEmptyScreen
import com.photowidget.ui.screens.home.WidgetListItem
import com.photowidget.ui.screens.home.WidgetListScreen
import com.photowidget.ui.screens.launch.LaunchScreen
import com.photowidget.ui.screens.privacy.PrivacyScreen
import com.photowidget.ui.screens.widgetsettings.WidgetSettingsScreen

/**
 * Replaces the previous boolean/int state-flag screen-switch in `MainActivity` with a real
 * `NavHost`. All widget-query orchestration (AppWidgetManager, BroadcastReceiver, repository)
 * stays in `MainActivity` — only navigation/routing lives here, matching the plan's decision not
 * to introduce a ViewModel for what is a UI-only rebuild.
 */
@Composable
fun PhotoWidgetNavHost(
    navController: NavHostController,
    widgetIds: IntArray,
    widgetItems: Map<Int, WidgetListItem>,
    startWidgetId: Int,
    onPinWidget: () -> Unit,
    loadWidgetConfig: suspend (Int) -> WidgetConfig,
    onSaveWidgetConfig: (Int, WidgetConfig) -> Unit,
    onResetWidget: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEmpty = widgetIds.isEmpty()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(isEmpty, currentRoute) {
        if (currentRoute == Routes.HomeEmpty && !isEmpty) {
            navController.navigate(Routes.HomeList) { popUpTo(Routes.HomeEmpty) { inclusive = true } }
        } else if (currentRoute == Routes.HomeList && isEmpty) {
            navController.navigate(Routes.HomeEmpty) { popUpTo(Routes.HomeList) { inclusive = true } }
        }
    }

    NavHost(navController = navController, startDestination = Routes.Launch, modifier = modifier) {
        composable(Routes.Launch) {
            LaunchScreen(
                onFinished = {
                    val target = when {
                        startWidgetId != -1 -> Routes.widgetSettings(startWidgetId)
                        isEmpty -> Routes.HomeEmpty
                        else -> Routes.HomeList
                    }
                    navController.navigate(target) { popUpTo(Routes.Launch) { inclusive = true } }
                },
            )
        }

        composable(Routes.HomeEmpty) {
            HomeEmptyScreen(
                onPinWidget = onPinWidget,
                onOpenSettings = { navController.navigate(Routes.AppSettings) },
            )
        }

        composable(Routes.HomeList) {
            var resettingWidgetId by remember { mutableIntStateOf(-1) }
            WidgetListScreen(
                widgetIds = widgetIds,
                widgetItems = widgetItems,
                onEditWidget = { navController.navigate(Routes.widgetSettings(it)) },
                onResetWidget = { resettingWidgetId = it },
                onPinWidget = onPinWidget,
                onOpenSettings = { navController.navigate(Routes.AppSettings) },
            )
            if (resettingWidgetId != -1) {
                val target = resettingWidgetId
                ResetWidgetDialog(
                    onConfirm = {
                        onResetWidget(target)
                        resettingWidgetId = -1
                    },
                    onDismiss = { resettingWidgetId = -1 },
                )
            }
        }

        composable(
            route = Routes.WidgetSettings,
            arguments = listOf(navArgument(Routes.WidgetIdArg) { type = NavType.IntType }),
        ) { backStackEntry ->
            val widgetId = backStackEntry.arguments?.getInt(Routes.WidgetIdArg) ?: return@composable
            var config by remember(widgetId) { mutableStateOf<WidgetConfig?>(null) }
            LaunchedEffect(widgetId) { config = loadWidgetConfig(widgetId) }
            val loaded = config
            if (loaded != null) {
                WidgetSettingsScreen(
                    initialConfig = loaded,
                    onSave = { saved ->
                        onSaveWidgetConfig(widgetId, saved)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() },
                )
            }
        }

        composable(Routes.AppSettings) {
            AppSettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateLanguage = { navController.navigate(Routes.Language) },
                onNavigateAbout = { navController.navigate(Routes.About) },
                onNavigatePrivacy = { navController.navigate(Routes.Privacy) },
            )
        }
        composable(Routes.Language) { LanguageScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.About) { AboutScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.Privacy) { PrivacyScreen(onBack = { navController.popBackStack() }) }
    }
}
