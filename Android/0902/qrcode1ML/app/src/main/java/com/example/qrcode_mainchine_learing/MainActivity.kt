package com.example.qrcode_mainchine_learing

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : AppCompatActivity() {
    private var scanReady = false
    private lateinit var textViewData: TextView
    private lateinit var buttonScan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonScan = findViewById(R.id.button_scan)
        textViewData = findViewById(R.id.textView_data)
        textViewData.text = ""

        buttonScan.isEnabled = false
        prefetchScannerModule()

        buttonScan.setOnClickListener {
            textViewData.text = ""
            startScan()
        }
    }

    private fun prefetchScannerModule() {
        val scannerApi = GmsBarcodeScanning.getClient(this@MainActivity)
        val module = ModuleInstall.getClient(this@MainActivity)
        val request = ModuleInstallRequest.newBuilder()
            .addApi(scannerApi)
            .build()

        module.installModules(request).addOnSuccessListener {
            scanReady = true
            buttonScan.postDelayed({
                buttonScan.isEnabled = true
            }, 3000)
        }.addOnFailureListener {
            scanReady = false
            buttonScan.isEnabled = true
            Toast.makeText(this@MainActivity, "API download fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startScan() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = GmsBarcodeScanning.getClient(this, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                textViewData.text = rawValue ?: "無法讀取內容"
            }
            .addOnCanceledListener {
                textViewData.text = "掃描已取消"
            }
            .addOnFailureListener { e ->
                textViewData.text = "掃描錯誤: ${e.message}"
            }
    }
}
