package com.photowidget.widget

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

object WidgetUriHelper {

    fun ensureReadPermission(context: Context, uri: Uri) {
        try {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        } catch (_: SecurityException) {
        }
    }

    fun grantLauncherReadAccess(context: Context, uri: Uri) {
        // Do NOT use for widget ImageView delivery — see AGENTS.md.
        val launcherPackage = resolveLauncherPackageName(context) ?: return
        try {
            context.grantUriPermission(
                launcherPackage,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        } catch (_: Exception) {
        }
    }

    private fun resolveLauncherPackageName(context: Context): String? {
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        val resolveInfo = context.packageManager.resolveActivity(
            homeIntent,
            PackageManager.MATCH_DEFAULT_ONLY,
        )
        return resolveInfo?.activityInfo?.packageName
    }
}
