package io.applaunch.applaunchmini.utils


import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.view.activity.SplashScreen
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class CommonUtils {


    companion object {
        private const val PREFS_NAME = "com.malasfruit.malas_all_in_one"
        private val TAG = CommonUtils::class.java.simpleName

        private var dialog: AlertDialog? = null


        /**
         * Check Internet Connection.
         */
        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
                .isConnected
        }

        /**
         * Get Today's Date.
         */
        fun getToDaysDate(): String? {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal: Calendar = Calendar.getInstance()
            return dateFormat.format(cal.time)
        }

        /**
         * Dismiss ProgressBar.
         */
        private fun dismissProgress() {
            if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
        }

        /** To check if Image is Set on ImageView or Not */
        fun isImageSetOnView(img: ImageView): Boolean {
            return img.drawable == null
        }

        /** To Check If Mobile No. is Valid Or Not */
        fun isValidMobile(phone: String?): Boolean {
            val regex = "^(?=.*[0-9]).{10}$"
            return Pattern.compile(regex).matcher(phone!!).matches()
        }

        /** To check If Email is Valid Or Not */
        fun isValidEmail(email: String?): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
        }

        /** To Check If the PinCode is Valid or Not*/
        fun isValidPinCode(pinCode: String?): Boolean {

            // Regex to check valid pin code of India.
            val regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$"

            // Compile the ReGex
            val p: Pattern = Pattern.compile(regex)

            // If the pin code is empty
            // return false
            if (pinCode == null) {
                return false
            }

            // Pattern class contains matcher() method
            // to find matching between given pin code
            // and regular expression.
            val m: Matcher = p.matcher(pinCode)

            // Return if the pin code
            // matched the ReGex
            return m.matches()
        }


        /** To Get Current Time */
        fun getCurrentTime(): String? {
            val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val cal: Calendar = Calendar.getInstance()
            return dateFormat.format(cal.time)
        }

        /** TO check if Service is running in foreground */
        fun isServiceRunningInForeground(context: Context, serviceClass: Class<*>): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return service.foreground
                }
            }
            return false
        }

        /** Create Notification Channel's */
        @RequiresApi(Build.VERSION_CODES.O)
        fun createNotificationChannel(
            channelId: String?,
            channelName: String?,
            context: Context
        ): String? {
            val channel = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH
            )
            channel.lightColor = Color.BLUE
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
            return channelId
        }

        /** Create Notifications */
        fun createNotification(
            context: Context,
            serviceName: String?,
            notificationTitle: String?
        ): Notification {
            var channelId: String? = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channelId = createNotificationChannel(
                    "default",
                    serviceName,
                    context
                )
            }

            val builder: NotificationCompat.Builder = channelId!!.let {
                NotificationCompat.Builder(
                    context,
                    it
                ) //                .setSmallIcon(R.drawable.notification_icon)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentTitle(getAppLabel(context))
                    .setContentText(notificationTitle)
                    .setColor(ContextCompat.getColor(context, R.color.purple_700))
                    .setCategory(NotificationCompat.CATEGORY_SERVICE)
            }
            val intent = Intent(context, SplashScreen::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
            } else {

                builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
            }
            return builder.build()
        }

        /** Get App label */
        private fun getAppLabel(context: Context): String {
            val packageManager: PackageManager = context.packageManager
            var applicationInfo: ApplicationInfo? = null
            try {
                applicationInfo =
                    packageManager.getApplicationInfo(context.applicationInfo.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                (e.message)?.let { Log.e(TAG, it) }
            }
            return (if (applicationInfo != null) packageManager.getApplicationLabel(applicationInfo) else "Unknown") as String
        }

        /** convert Image Into String */
        fun getStringImage(bmp: Bitmap): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            val encodedImage: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            return "data:image/png;base64,$encodedImage"
        }

        /** Rotate Image From Landscape to Portrait */
        fun getPortraitBitmap(bitmaps: Bitmap): Bitmap {
            var bitmap = bitmaps
            if (bitmap.width > bitmap.height) {
                val mat = Matrix()
                mat.postRotate(90F)
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, mat, true)
            }
            return bitmap
        }


        /** Show Dialog */
        fun showDialog(context: Context, title: String, message: String) {
            val alert = AlertDialog.Builder(context)
            alert.setTitle(title).setMessage(message)
                .setPositiveButton("Ok", null)
            val alert1 = alert.create()
            alert1.show()
        }
    }
}