package me.chicchi7393.sburrapp

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import me.chicchi7393.sburrapp.ui.components.BottomBar
import me.chicchi7393.sburrapp.ui.components.MainContent
import me.chicchi7393.sburrapp.ui.components.TopBar
import me.chicchi7393.sburrapp.ui.theme.SburrappTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { _: Boolean ->
        // non freca
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        askNotificationPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("AGG_SBURRAT", "Sburrate dagli amici", NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = "Quandosbu"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        setContent {
            SburrappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val selectedItem = remember { mutableStateOf(0) }

    Scaffold(topBar = { TopBar() }, bottomBar = { BottomBar(selectedItem) }) {
        MainContent(selectedItem = selectedItem, paddingScaffold = it)
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    SburrappTheme {
        Content()
    }
}