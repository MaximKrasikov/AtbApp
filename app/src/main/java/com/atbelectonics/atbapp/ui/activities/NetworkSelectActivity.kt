package com.atbelectonics.atbapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.atbelectonics.atbapp.api.Echo
import com.atbelectonics.atbapp.api.Message
import com.atbelectonics.atbapp.databinding.ActivityNetworkSelectBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class NetworkSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNetworkSelectBinding
    private lateinit var webSocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("ws://atb2100.local/ws")
            //.url("ws://10.20.30.40/ws")
            .build()

        Log.e("Request", request.toString())

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.i("INFO", "Connected")
                sendRequestForAPavailable()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {

                //this@MainActivity?.runOnUiThread {
                runOnUiThread {
                    binding.terminalField.text = text
                    val p = Gson().fromJson(text, Echo::class.java)
                    Log.e("Received", text)
                    Log.e("Decerealized", p.toString())
                    Log.e("FID", p.FID)
                    Log.e("RID", p.RID)
                    Log.e("STA", p.ARG?.AP.toString())

                    if (p.ARG?.STA.toString().toInt() != 0) {
                        sendRequestForAPavailable()
                    } else {
                        binding.stationsFoundValueField.text = p.ARG?.AP.toString()
                    }
                    //Log.e("ARG", p.ARG)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                // Connection closing
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // Connection closed
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // Connection failed
                Log.i("INFO", "onFailure")
                //t.printStackTrace()
            }
        })

        binding.buttonSearch.setOnClickListener {
            sendRequestForAPavailable()
        }
    }

    fun sendRequestForAPavailable() {
        val publish = Gson().toJson(Message(FID = "E1F15CA1", RID = "4442"))
        //val publish = Gson().toJson(Message("global", "publish", message))
        Log.i("Message", publish)
        webSocket.send(publish)
    }
}