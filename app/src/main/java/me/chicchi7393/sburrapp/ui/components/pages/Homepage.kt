package me.chicchi7393.sburrapp.ui.components.pages

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import me.chicchi7393.sburrapp.callback.changeFcmCallback
import me.chicchi7393.sburrapp.callback.registerCallback
import me.chicchi7393.sburrapp.callback.getFriendsCallback
import me.chicchi7393.sburrapp.callback.sburratoCallback
import me.chicchi7393.sburrapp.helpers.DatastoreHelper
import me.chicchi7393.sburrapp.helpers.HoSburratoHTTP
import me.chicchi7393.sburrapp.helpers.Singleton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random
import kotlin.system.exitProcess

@Composable
fun Homepage(retrofit: HoSburratoHTTP) {
    val context = LocalContext.current
    val datastoreHelper = DatastoreHelper(context)
    val coroutineScope = rememberCoroutineScope()
    val deviceIdFlow = datastoreHelper.getDeviceId()
    val showModal = remember {
        mutableStateOf(false)
    }

    var usernameInput by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }

    var consistencyInput by rememberSaveable { mutableStateOf("") }
    var honourInput by rememberSaveable { mutableStateOf("") }
    var whereInput by rememberSaveable { mutableStateOf("") }

    var consistencyError by rememberSaveable { mutableStateOf(false) }
    var whereError by rememberSaveable { mutableStateOf(false) }

    var inputError by remember {
        mutableStateOf(false)
    }

    var passwordError by remember {
        mutableStateOf(false)
    }

    var toShowDetail by remember {
        mutableStateOf(false)
    }

    var fcm: String? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = "deviceidCollect") {
        coroutineScope.launch {
            deviceIdFlow.collect {
                showModal.value = it == null
                Singleton.deviceId = it
            }
            fcm = Firebase.messaging.token.await()
        }
    }

    if ((context as Activity).intent.getStringExtra("username") != null && !Singleton.ack) {
        toShowDetail = true
    }

    if (toShowDetail) {
        Dialog(onDismissRequest = {
            toShowDetail = false
            Singleton.ack = true
        }, properties = DialogProperties(securePolicy = SecureFlagPolicy.SecureOn)) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = listOf(
                            "Un tuo amico ha appena sborrato!",
                            "Entro a gamba tesa, leccandoti il ciolone...",
                            "Ambatakam",
                            "Ucci ucci, sento odore di cacasburrucci!",
                            "Ho sburrato",
                            "Lago duria per il tuo amico",
                            "Un tuo amico sta godendo"
                        )[Random.nextInt(5)],
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val cummer = context.intent.getStringExtra("username")
                    Text("Sborratore: $cummer", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    val at = SimpleDateFormat("dd-MM-yyyy' alle 'HH:mm:ss", Locale.ITALY).format(
                        Date(context.intent.getStringExtra("at")?.toLong() ?: 0L)
                    )
                    Text("Quando? Il $at", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    val con = context.intent.getStringExtra("con")
                    Text("La consistenza della sburra era $con", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    val hon = context.intent.getStringExtra("hon")
                    if (hon != "") {
                        Text("Questa sborrata era dedicata a $hon", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    val where = context.intent.getStringExtra("where")!!.replaceFirstChar {
                        it.uppercase()
                    }
                    Text("La sborra di $cummer dov'è andata? $where", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    TextButton(
                        onClick = {
                            toShowDetail = false
                            Singleton.ack = true
                                  },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Grazie per l'informazione")
                    }
                }
            }
        }
    }

    if (showModal.value) {
        Dialog(onDismissRequest = {
            context.finish()
            exitProcess(0)
        }) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Benvenuto a Sburrapp! Prima di tutto, registrati!\nL'username non deve contenere spazi e deve essere di almeno 4 caratteri, la password invece deve essere di almeno 8 caratteri",
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = usernameInput,
                        onValueChange = {
                            usernameInput = it
                            inputError = false },
                        label = { Text("Username") },
                        placeholder = { Text("sburramaster43") },
                        isError = inputError
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = passwordInput,
                        onValueChange = {
                            passwordInput = it
                            inputError = false },
                        label = { Text("Password") },
                        placeholder = { Text("h0Sb0rr4to") },

                        isError = passwordError,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = {
                            if (usernameInput.length < 4 || usernameInput.contains(" ")) {
                                inputError = true
                            } else if (passwordInput.length < 8 || passwordInput.contains(" ")) {
                                passwordError = true
                            } else {
                                retrofit.registra(usernameInput, passwordInput).enqueue(registerCallback(showModal, coroutineScope, datastoreHelper, context))
                                if (fcm != null && Singleton.deviceId != null && Singleton.deviceId != "") {
                                    retrofit.cambiaFcm(Singleton.deviceId!!, fcm!!).enqueue(changeFcmCallback(context))
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Confirm")
                    }
                }
            }

        }
    } else {
        if (Singleton.deviceId != null && Singleton.deviceId != "") {
            retrofit.getFriends(Singleton.deviceId!!).enqueue(getFriendsCallback(context))
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = consistencyInput,
                onValueChange = {
                    consistencyInput = it
                    consistencyError = false },
                label = { Text("Consistenza *") },
                placeholder = { Text("Normale") },
                isError = consistencyError, keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = honourInput,
                onValueChange = {
                    honourInput = it
                },
                label = { Text("In onore di?") },
                placeholder = { Text("J0ker") },
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = whereInput,
                onValueChange = {
                    whereInput = it
                    whereError = false },
                label = { Text("Dove hai sburrato? *") },
                placeholder = { Text("Nel cesso") },
                isError = whereError, keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    consistencyError = consistencyInput == ""
                    whereError = whereInput == ""
                    if (!consistencyError && !whereError && Singleton.deviceId != null && Singleton.deviceId != "") {
                        retrofit.hoSburratoReq(Singleton.deviceId!!, consistencyInput, honourInput, whereInput).enqueue(sburratoCallback(context))
                    }
                    consistencyInput = ""
                    whereInput = ""
                    honourInput = ""
                }, modifier = Modifier) {
                    Text("Ho sburrato")
                }
            }

        }

    }
}