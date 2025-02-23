package com.example.callcontroller

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.callcontroller.databinding.ActivityMainBinding
import com.example.callcontroller.service.CallService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_CODE = 123

    private val requiredPermissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAndRequestPermissions()
        setupUI()
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) 
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            startCallService()
        }
    }

    private fun setupUI() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        
        if (ActivityCompat.checkSelfPermission(this, 
            Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED) {
            binding.phoneNumberText.text = "Phone Number: ${telephonyManager.line1Number}"
            binding.simOperatorText.text = "Operator: ${telephonyManager.simOperatorName}"
        }

        binding.connectButton.setOnClickListener {
            startCallService()
            Toast.makeText(this, "Conectado al servidor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCallService() {
        val serviceIntent = Intent(this, CallService::class.java)
        startService(serviceIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startCallService()
            } else {
                Toast.makeText(
                    this,
                    "Se necesitan todos los permisos para funcionar correctamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
