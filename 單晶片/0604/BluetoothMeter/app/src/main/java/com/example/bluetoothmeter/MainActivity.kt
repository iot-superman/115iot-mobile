
package com.example.bluetoothmeter

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.bluetoothmeter.ui.theme.BluetoothMeterTheme
import java.io.InputStream
import java.util.UUID
import kotlin.concurrent.thread

// =====================================================
// 藍牙裝置資料
// =====================================================

data class DeviceItem(

    val device: BluetoothDevice,

    val rssi: Int
)

class MainActivity : ComponentActivity() {

    // =====================================================
    // Bluetooth
    // =====================================================

    private var bluetoothAdapter: BluetoothAdapter? = null

    private var bluetoothSocket: BluetoothSocket? = null

    private var inputStream: InputStream? = null

    // =====================================================
    // 真正連線狀態
    // =====================================================

    private var isConnected = false

    // =====================================================
    // HC-05 UUID (SPP)
    // =====================================================

    private val uuid: UUID =
        UUID.fromString(
            "00001101-0000-1000-8000-00805F9B34FB"
        )

    // =====================================================
    // Compose State
    // =====================================================

    private var adcValue by mutableStateOf(0)

    private var connectStatus by mutableStateOf("未連線")

    private val deviceList =
        mutableStateListOf<DeviceItem>()

    // =====================================================
    // Bluetooth Receiver
    // =====================================================

    private val bluetoothReceiver =

        object : BroadcastReceiver() {

            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {

                when (intent?.action) {

                    BluetoothDevice.ACTION_FOUND -> {

                        val device: BluetoothDevice? =

                            if (
                                Build.VERSION.SDK_INT >=
                                Build.VERSION_CODES.TIRAMISU
                            ) {

                                intent.getParcelableExtra(
                                    BluetoothDevice.EXTRA_DEVICE,
                                    BluetoothDevice::class.java
                                )

                            } else {

                                @Suppress("DEPRECATION")

                                intent.getParcelableExtra(
                                    BluetoothDevice.EXTRA_DEVICE
                                )
                            }

                        device?.let {

                            // =====================================
                            // RSSI
                            // =====================================

                            val rssi =

                                intent.getShortExtra(
                                    BluetoothDevice.EXTRA_RSSI,
                                    Short.MIN_VALUE
                                ).toInt()

                            // =====================================
                            // 是否已存在
                            // =====================================

                            val existsIndex =

                                deviceList.indexOfFirst { item ->

                                    item.device.address ==
                                            it.address
                                }

                            // =====================================
                            // 更新 RSSI
                            // =====================================

                            if (existsIndex >= 0) {

                                deviceList[existsIndex] =

                                    DeviceItem(
                                        device = it,
                                        rssi = rssi
                                    )

                            } else {

                                deviceList.add(

                                    DeviceItem(
                                        device = it,
                                        rssi = rssi
                                    )
                                )
                            }

                            // =====================================
                            // RSSI 排序
                            // 強 → 弱
                            // =====================================

                            val sorted =

                                deviceList.sortedByDescending { item ->

                                    item.rssi
                                }

                            deviceList.clear()

                            deviceList.addAll(sorted)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // =================================================
        // 初始化藍牙
        // =================================================

        bluetoothAdapter =
            BluetoothAdapter.getDefaultAdapter()

        // =================================================
        // 權限
        // =================================================

        requestBluetoothPermission()

        // =================================================
        // 註冊 Receiver
        // =================================================

        val filter =
            IntentFilter(BluetoothDevice.ACTION_FOUND)

        registerReceiver(
            bluetoothReceiver,
            filter
        )

        // =================================================
        // Compose UI
        // =================================================

        setContent {

            BluetoothMeterTheme {

                Surface(

                    modifier = Modifier.fillMaxSize(),

                    color =
                        MaterialTheme.colorScheme.background

                ) {

                    BluetoothScreen(

                        adc = adcValue,

                        status = connectStatus,

                        deviceList = deviceList,

                        onScanClick = {

                            startBluetoothScan()
                        },

                        onDisconnectClick = {

                            disconnectBluetooth()
                        },

                        onDeviceClick = { device ->

                            connectBluetooth(device)
                        }
                    )
                }
            }
        }
    }

    // =====================================================
    // 權限
    // =====================================================

    private fun requestBluetoothPermission() {

        val permissionList =
            mutableListOf<String>()

        permissionList.add(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        permissionList.add(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S
        ) {

            permissionList.add(
                Manifest.permission.BLUETOOTH_CONNECT
            )

            permissionList.add(
                Manifest.permission.BLUETOOTH_SCAN
            )
        }

        requestPermissions(
            permissionList.toTypedArray(),
            100
        )
    }

    // =====================================================
    // 掃描藍牙
    // =====================================================

    private fun startBluetoothScan() {

        try {

            deviceList.clear()

            connectStatus = "掃描中..."

            if (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            bluetoothAdapter?.cancelDiscovery()

            bluetoothAdapter?.startDiscovery()

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    // =====================================================
    // 斷開藍牙
    // =====================================================

    private fun disconnectBluetooth() {

        try {

            isConnected = false

            bluetoothAdapter?.cancelDiscovery()

            inputStream?.close()

            bluetoothSocket?.close()

            inputStream = null

            bluetoothSocket = null

            adcValue = 0

            connectStatus = "已斷線"

        } catch (e: Exception) {

            e.printStackTrace()

            connectStatus = "斷線失敗"
        }
    }

    // =====================================================
    // 連接藍牙
    // =====================================================

    private fun connectBluetooth(

        device: BluetoothDevice
    ) {

        try {

            connectStatus =
                "連線中: ${device.name}"

            thread {

                try {

                    // =====================================
                    // 先停止掃描
                    // =====================================

                    bluetoothAdapter?.cancelDiscovery()

                    Thread.sleep(1000)

                    if (
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@thread
                    }

                    // =====================================
                    // 尚未配對 → 先配對
                    // =====================================

                    if (
                        device.bondState !=
                        BluetoothDevice.BOND_BONDED
                    ) {

                        runOnUiThread {

                            connectStatus = "配對中..."
                        }

                        device.createBond()

                        // =================================
                        // 等待配對完成
                        // =================================

                        var waitCount = 0

                        while (

                            device.bondState !=
                            BluetoothDevice.BOND_BONDED

                        ) {

                            Thread.sleep(500)

                            waitCount++

                            // =============================
                            // 最多等10秒
                            // =============================

                            if (waitCount > 20) {

                                runOnUiThread {

                                    connectStatus = "配對失敗"
                                }

                                return@thread
                            }
                        }
                    }

                    // =====================================
                    // 方法1：
                    // 標準 HC-05 UUID
                    // =====================================

                    try {

                        bluetoothSocket =

                            device.createRfcommSocketToServiceRecord(
                                uuid
                            )

                        bluetoothSocket?.connect()

                    } catch (e: Exception) {

                        e.printStackTrace()

                        try {

                            bluetoothSocket?.close()

                            // =================================
                            // 方法2：
                            // fallback socket
                            // =================================

                            val method =

                                device.javaClass.getMethod(
                                    "createRfcommSocket",
                                    Int::class.javaPrimitiveType
                                )

                            bluetoothSocket =

                                method.invoke(
                                    device,
                                    1
                                ) as BluetoothSocket

                            bluetoothSocket?.connect()

                        } catch (e2: Exception) {

                            e2.printStackTrace()

                            runOnUiThread {

                                connectStatus =
                                    "SPP 連線失敗"

                                Toast.makeText(

                                    this,

                                    "此裝置可能不是標準 HC-05",

                                    Toast.LENGTH_LONG

                                ).show()
                            }

                            return@thread
                        }
                    }

                    // =====================================
                    // InputStream
                    // =====================================

                    inputStream =
                        bluetoothSocket?.inputStream

                    // =====================================
                    // 已連線
                    // =====================================

                    isConnected = true

                    connectStatus =
                        "已連線: ${device.name}"

                    // =====================================
                    // 開始讀 ADC
                    // =====================================

                    readADCData()

                } catch (e: Exception) {

                    isConnected = false

                    runOnUiThread {

                        connectStatus = "連線失敗"

                        Toast.makeText(
                            this,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    // =====================================================
    // 讀取 ADC
    // =====================================================

    private fun readADCData() {

        thread {

            try {

                while (isConnected) {

                    // =====================================
                    // 低位元
                    // =====================================

                    val low =
                        inputStream?.read() ?: 0

                    // =====================================
                    // 高位元
                    // =====================================

                    val high =
                        inputStream?.read() ?: 0

                    // =====================================
                    // 合成 ADC
                    // =====================================

                    val adc =
                        low + (high shl 8)

                    adcValue = adc
                }

            } catch (e: Exception) {

                isConnected = false

                runOnUiThread {

                    connectStatus = "連線中斷"
                }
            }
        }
    }

    override fun onDestroy() {

        super.onDestroy()

        isConnected = false

        unregisterReceiver(bluetoothReceiver)

        bluetoothSocket?.close()
    }
}

// =========================================================
// Compose UI
// =========================================================

@androidx.compose.runtime.Composable
fun BluetoothScreen(

    adc: Int,

    status: String,

    deviceList: List<DeviceItem>,

    onScanClick: () -> Unit,

    onDisconnectClick: () -> Unit,

    onDeviceClick: (BluetoothDevice) -> Unit
) {

    // =====================================================
    // ADC 轉電壓
    // =====================================================

    val voltage =
        adc * 5.0 / 4095.0

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        // =================================================
        // Title
        // =================================================

        Text(

            text = "藍牙電表",

            fontSize = 34.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =================================================
        // 按鈕列
        // =================================================

        Row {

            // =============================================
            // 掃描按鈕
            // =============================================

            Button(

                onClick = {

                    onScanClick()
                }

            ) {

                Text("掃描附近藍牙")
            }

            Spacer(
                modifier = Modifier.width(20.dp)
            )

            // =============================================
            // 斷線按鈕
            // =============================================

            Button(

                onClick = {

                    onDisconnectClick()
                }

            ) {

                Text("斷線")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =================================================
        // 狀態
        // =================================================

        Text(

            text = status,

            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =================================================
        // 藍牙列表
        // =================================================

        Text(

            text = "附近藍牙裝置",

            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)

        ) {

            items(deviceList) { item ->

                BluetoothDeviceItem(

                    item = item,

                    onClick = {

                        onDeviceClick(item.device)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))

        // =================================================
        // ADC
        // =================================================

        Text(

            text = "ADC: $adc",

            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // =================================================
        // Voltage
        // =================================================

        Text(

            text = String.format(
                "電壓: %.2f V",
                voltage
            ),

            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // =================================================
        // ProgressBar
        // =================================================

        LinearProgressIndicator(

            progress = {

                adc / 4095f
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
    }
}

// =========================================================
// Device Card
// =========================================================

@androidx.compose.runtime.Composable
fun BluetoothDeviceItem(

    item: DeviceItem,

    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {

                onClick()
            }

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(

                text =
                    item.device.name
                        ?: "Unknown Device",

                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text =
                    item.device.address,

                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text =
                    "RSSI: ${item.rssi} dBm",

                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text = when {

                    item.rssi >= -55 ->
                        "訊號極強"

                    item.rssi >= -70 ->
                        "訊號良好"

                    item.rssi >= -85 ->
                        "訊號普通"

                    else ->
                        "訊號弱"
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text = when (

                    item.device.bondState

                ) {

                    BluetoothDevice.BOND_BONDED ->
                        "已配對"

                    BluetoothDevice.BOND_BONDING ->
                        "配對中"

                    else ->
                        "未配對"
                }
            )
        }
    }
}

