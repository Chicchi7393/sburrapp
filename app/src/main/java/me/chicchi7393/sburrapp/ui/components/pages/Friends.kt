package me.chicchi7393.sburrapp.ui.components.pages

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.launch
import me.chicchi7393.sburrapp.R
import me.chicchi7393.sburrapp.callback.addFriendsCallback
import me.chicchi7393.sburrapp.callback.deleteFriendsCallback
import me.chicchi7393.sburrapp.callback.getFriendsCallback
import me.chicchi7393.sburrapp.helpers.DatastoreHelper
import me.chicchi7393.sburrapp.helpers.HoSburratoHTTP
import me.chicchi7393.sburrapp.helpers.Singleton
import me.chicchi7393.sburrapp.ui.theme.SburrappTheme
import java.security.MessageDigest


@Composable
fun Friends(http: HoSburratoHTTP, selectedItem: MutableState<Int>) {
    val context = LocalContext.current
    val newFriendUser = remember {
        mutableStateOf("")
    }

    val newFriendUserError = remember {
        mutableStateOf(false)
    }

    val errorOpacity = remember {
        mutableStateOf(false)
    }

    val errorText = remember {
        mutableStateOf("")
    }

    val datastoreHelper = DatastoreHelper(context)
    val deviceIdFlow = datastoreHelper.getDeviceId()

    var deviceId: String? by remember {
        mutableStateOf(null)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "deviceidCollect") {
        coroutineScope.launch {
            deviceIdFlow.collect {
                deviceId = it
            }
        }
    }

    Column(Modifier.padding(10.dp)) {
        Text("Lista amici", style = MaterialTheme.typography.displayLarge.copy(
            fontWeight = FontWeight.SemiBold
        ))
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(Singleton.friends.value) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountCircle, "Icona utente", Modifier.scale(1.5f))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(it.username, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 10.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Icon(Icons.Default.Clear, "Elimina amico", Modifier.clickable {
                            if (deviceId != null) {
                                http.deleteFriend(deviceId!!, it.username).enqueue(
                                    deleteFriendsCallback(context, selectedItem)
                                )
                                http.getFriends(deviceId!!).enqueue(getFriendsCallback(context))
                            }
                        })
                    }
                }
            }
            items(1) {
                Spacer(Modifier.height(8.dp))
                Divider()
                Spacer(Modifier.height(8.dp))
                Text("Aggiungi amico", style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    TextField(value = newFriendUser.value, onValueChange = {
                        newFriendUser.value = it
                    }, isError = newFriendUserError.value, label = {
                        Text("Codice amico")
                    })
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        if (newFriendUser.value.length < 4 || newFriendUser.value.contains(" ")) {
                            newFriendUserError.value = true
                        } else {
                            if (deviceId != null) {
                                http.addFriend(deviceId!!, newFriendUser.value).enqueue(
                                    addFriendsCallback(
                                        context,
                                        errorOpacity,
                                        errorText,
                                        selectedItem
                                    )
                                )
                                http.getFriends(deviceId!!).enqueue(getFriendsCallback(context))
                            }
                        }
                    }) {
                        Icon(Icons.Default.Add, "aggiungi amico")
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        errorText.value,
                        Modifier.alpha(if (errorOpacity.value) 1f else 0f),
                        Color.Red
                    )
                }
                Spacer(Modifier.height(16.dp))
                if (deviceId != null) {
                    val bytes = deviceId!!.toByteArray()
                    val md = MessageDigest.getInstance("SHA-256")
                    val digest = md.digest(bytes)
                    val hashed = digest.fold("") { str, itHash -> str + "%02x".format(itHash) }.take(15)
                    val friendCodeCalc = hashed.chunked(5).joinToString("-")
                    Row {
                        Text(
                            "Il tuo codice amico è $friendCodeCalc",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Icon(painterResource(R.drawable.content_copy), "Copia", modifier = Modifier.clickable {
                                val clipboard = getSystemService(context, ClipboardManager::class.java)
                                val clip = ClipData.newPlainText("friendCode", friendCodeCalc)
                                clipboard?.setPrimaryClip(clip)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FriendsPreview() {
    SburrappTheme {
        //Friends()
    }
}