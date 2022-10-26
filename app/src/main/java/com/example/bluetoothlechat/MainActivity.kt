/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.bluetoothlechat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bluetoothlechat.bluetooth.ChatServer

class MainActivity : AppCompatActivity() {

    /**
     * This block is for requesting permissions up to Android 12+
     *
     */
    private val PERMISSIONS_REQUEST_CODE = 191
    private val BLE_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @SuppressLint("InlinedApi")
    private val ANDROID_12_BLE_PERMISSIONS = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    fun requestBlePermissions(activity: Activity?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ActivityCompat.requestPermissions(
            activity!!,
            ANDROID_12_BLE_PERMISSIONS,
            requestCode
        ) else ActivityCompat.requestPermissions(
            activity!!, BLE_PERMISSIONS, requestCode
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestBlePermissions(this, PERMISSIONS_REQUEST_CODE)
    }

    // Run the chat server as long as the app is on screen
    override fun onStart() {
        super.onStart()
        ChatServer.startServer(application)
    }

    override fun onStop() {
        super.onStop()
        ChatServer.stopServer()
    }
}