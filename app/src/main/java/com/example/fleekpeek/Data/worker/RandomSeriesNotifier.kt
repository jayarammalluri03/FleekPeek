package com.example.fleekpeek.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.fleekpeek.R

object RandomSeriesNotifier {

    private const val CHANNEL_ID = "random_series_recommendations"
    private const val CHANNEL_NAME = "Series recommendations"
    private const val CHANNEL_DESC = "Random TV series from TMDB"

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun showSeriesNotification(
        context: Context,
        title: String,
        overview: String?,
        imgUrl: String?
    ) {
        createChannelIfNeeded(context)
        val imageUrl = imgUrl?.let { "https://image.tmdb.org/t/p/w342$it" }
        val bitmap  = imageUrl?.let { loadBitmapWithCoil(context, it) }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.mipmapmdpi)
            .setContentTitle(title)
            .setContentText(overview ?: "Check out this series!")
            .setStyle(NotificationCompat.BigTextStyle().bigText(overview ?: "Check out this series!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(bitmap)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createChannelIfNeeded(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = manager.getNotificationChannel(CHANNEL_ID)
            if (channel != null) return

            val newChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESC
            }

            manager.createNotificationChannel(newChannel)
        }
    }

    suspend fun loadBitmapWithCoil(context: Context, url: String): Bitmap? {
        return try {
            val loader = ImageLoader(context)

            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // IMPORTANT: disables hardware bitmaps so we can get Bitmap
                .build()

            val result = loader.execute(request)
            (result.drawable as? BitmapDrawable)?.bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
