package com.example.callcontroller.service

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.util.*

class CallService : Service() {
    private lateinit var webSocket: WebSocket
    private val gson = Gson()
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val TAG = "CallService"

    override fun onCreate() {
        super.onCreate()
        connectWebSocket()
    }

    private fun connectWebSocket() {
        val client = OkHttpClient()
        val deviceId = UUID.randomUUID().toString()

        // Use the server's host from the current connection
        val serverUrl = "ws://${SERVER_HOST}:${SERVER_PORT}/ws?deviceId=$deviceId"
        Log.d(TAG, "Conectando a: $serverUrl")

        val request = Request.Builder()
            .url(serverUrl)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket conectado")
                sendDeviceInfo()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "Mensaje recibido: $text")
                handleWebSocketMessage(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "Error en WebSocket: ${t.message}")
                // Implementar reconexión después de un tiempo
            }
        })
    }

    private fun sendDeviceInfo() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val deviceInfo = JSONObject().apply {
            put("type", "deviceInfo")
            put("phoneNumber", telephonyManager.line1Number)
            put("simOperator", telephonyManager.simOperatorName)
            put("simSlot", 1)
        }
        webSocket.send(deviceInfo.toString())
    }

    private fun handleWebSocketMessage(message: String) {
        try {
            val command = gson.fromJson(message, Command::class.java)

            when (command.command) {
                "dial" -> {
                    command.phoneNumber?.let { number ->
                        makePhoneCall(number)
                    }
                }
                "hangup" -> {
                    // Implementar lógica para colgar llamada
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al procesar mensaje: ${e.message}")
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    data class Command(
        val command: String,
        val phoneNumber: String? = null,
        val timestamp: String? = null
    )

    companion object {
        // These values will be replaced during build
        const val SERVER_HOST = "10.0.2.2" // Default for Android Emulator
        const val SERVER_PORT = "5000"
    }
}