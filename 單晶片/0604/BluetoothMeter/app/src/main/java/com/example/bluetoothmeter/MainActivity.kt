
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.compose.ui.tooling.preview.Preview
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
                            // 排序規則
                            // 1. 有名稱裝置排前面
                            // 2. 未知裝置排最後
                            // 3. RSSI 強 → 弱
                            // =====================================

                            val sorted =

                                deviceList.sortedWith(

                                    compareBy<DeviceItem> {

                                        val name =

                                            if (
                                                Build.VERSION.SDK_INT >=
                                                Build.VERSION_CODES.S
                                            ) {

                                                it.device.name

                                            } else {

                                                @Suppress("DEPRECATION")

                                                it.device.name
                                            }

                                        name.isNullOrBlank() ||
                                                name == "Unknown Device" ||
                                                name == "未知裝置"
                                    }

                                        .thenByDescending {

                                            it.rssi
                                        }
                                )

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

                    color = MaterialTheme.colorScheme.background

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
    // 權限要求
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

            if (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            connectStatus =
                "連線中: ${device.name ?: "Unknown"}"

            thread {

                try {

                    // =====================================
                    // 先停止掃描
                    // =====================================

                    bluetoothAdapter?.cancelDiscovery()

                    Thread.sleep(1000)

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

                                    this@MainActivity,

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

                    isConnected = true

                    runOnUiThread {

                        connectStatus =
                            "已連線: ${device.name ?: "Unknown"}"
                    }

                    // =====================================
                    // 開始讀 ADC
                    // =====================================

                    readADCData()

                } catch (e: Exception) {

                    isConnected = false

                    runOnUiThread {

                        connectStatus = "連線失敗"

                        Toast.makeText(
                            this@MainActivity,
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
    // 讀取 ADC 數據
    // =====================================================

    private fun readADCData() {

        thread {

            try {

                while (isConnected) {

                    val low =
                        inputStream?.read() ?: break

                    val high =
                        inputStream?.read() ?: break

                    // =====================================
                    // 串流中斷
                    // =====================================

                    if (
                        low == -1 ||
                        high == -1
                    ) break

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
// Compose UI 主介面
// =========================================================

@Composable
fun BluetoothScreen(

    adc: Int,

    status: String,

    deviceList: List<DeviceItem>,

    onScanClick: () -> Unit,

    onDisconnectClick: () -> Unit,

    onDeviceClick: (BluetoothDevice) -> Unit

) {

    val voltage =
        adc * 5.0 / 4095.0

    // =====================================================
    // 搜尋文字
    // =====================================================

    var searchText by remember {

        mutableStateOf("")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        Text(
            text = "藍牙電表",
            fontSize = 34.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // =================================================
        // 按鈕列
        // =================================================

        Row(

            modifier = Modifier.fillMaxWidth()

        ) {

            Button(

                onClick = {

                    onScanClick()
                },

                modifier = Modifier.weight(1f)

            ) {

                Text("掃描附近藍牙")
            }

            Spacer(
                modifier = Modifier.width(20.dp)
            )

            Button(

                onClick = {

                    onDisconnectClick()
                },

                modifier = Modifier.weight(1f)

            ) {

                Text("斷線")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = status,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "附近藍牙裝置",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // =================================================
        // 搜尋框
        // =================================================

        OutlinedTextField(

            value = searchText,

            onValueChange = {

                searchText = it
            },

            modifier = Modifier.fillMaxWidth(),

            label = {

                Text("搜尋藍牙名稱")
            },

            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // =================================================
        // 藍牙列表
        // =================================================

        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {

            items(

                deviceList.filter { item ->

                    val deviceName =

                        if (
                            Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.S
                        ) {

                            item.device.name ?: ""

                        } else {

                            @Suppress("DEPRECATION")

                            item.device.name ?: ""
                        }

                    // =====================================
                    // 搜尋過濾
                    // =====================================

                    searchText.isBlank() ||

                            deviceName.contains(
                                searchText,
                                ignoreCase = true
                            )
                }

            ) { item ->

                BluetoothDeviceItem(

                    item = item,

                    onClick = {

                        onDeviceClick(item.device)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        // =================================================
        // ADC
        // =================================================

        Text(
            text = "ADC: $adc",
            fontSize = 38.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(

            text = String.format(
                "電壓: %.2f V",
                voltage
            ),

            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(

            progress = {

                adc / 4095f
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
    }
}

// =========================================================
// Device Card
// =========================================================

@Composable
fun BluetoothDeviceItem(

    item: DeviceItem,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable {

                onClick()
            }
    ) {

        Column(

            modifier = Modifier.padding(

                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {

            // =============================================
            // 裝置名稱
            // =============================================

            val deviceName =

                if (
                    Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.S
                ) {

                    item.device.name ?: "未知裝置"

                } else {

                    @Suppress("DEPRECATION")

                    item.device.name ?: "未知裝置"
                }

            // =============================================
            // 第一行
            // 名稱 + 配對狀態
            // =============================================

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                // =========================================
                // 名稱
                // =========================================

                Text(

                    text = deviceName,

                    fontSize = 18.sp,

                    modifier = Modifier.weight(1f),

                    maxLines = 1
                )

                // =========================================
                // 配對狀態
                // =========================================

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
                    },

                    fontSize = 14.sp,

                    color = when (

                        item.device.bondState

                    ) {

                        BluetoothDevice.BOND_BONDED ->
                            Color(0xFF388E3C)

                        else ->
                            Color.Gray
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            // =============================================
            // 第二行
            // MAC + RSSI
            // =============================================

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                // =========================================
                // MAC
                // =========================================

                Text(

                    text = item.device.address,

                    fontSize = 12.sp,

                    color = Color.Gray
                )

                // =========================================
                // RSSI
                // =========================================

                Row {

                    Text(

                        text = "${item.rssi} dBm",

                        fontSize = 12.sp
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(

                        text = when {

                            item.rssi >= -55 ->
                                "極強"

                            item.rssi >= -70 ->
                                "良好"

                            item.rssi >= -85 ->
                                "普通"

                            else ->
                                "弱"
                        },

                        fontSize = 12.sp,

                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// =========================================================
// Preview
// =========================================================

@Preview(showBackground = true)
@Composable
fun BluetoothScreenPreview() {
    BluetoothMeterTheme {
        BluetoothScreen(
            adc = 2048,
            status = "已連線 (預覽模式)",
            deviceList = emptyList(),
            onScanClick = {},
            onDisconnectClick = {},
            onDeviceClick = {}
        )
    }
}
