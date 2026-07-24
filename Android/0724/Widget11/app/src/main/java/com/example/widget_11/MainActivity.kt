package com.example.widget_11

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etMuffinQty: EditText
    private lateinit var etPancakeQty: EditText
    private lateinit var etWaffleQty: EditText
    private lateinit var etDrinkQty: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnCancel: Button
    private lateinit var btnCheckout: Button
    private lateinit var btnDrink1: Button
    private lateinit var btnDrink2: Button
    private lateinit var cbMuffin: CheckBox
    private lateinit var cbPancake: CheckBox
    private lateinit var cbWaffle: CheckBox
    private lateinit var rbCola: RadioButton
    private lateinit var rbTea: RadioButton
    private lateinit var rbCoffee: RadioButton
    private lateinit var rgDrinks: RadioGroup
    
    private var drinkNum = 1

    private fun updateMenu(menuNum: Int) {
        rgDrinks.clearCheck()
        drinkNum = menuNum
        if (menuNum == 1) {
            rbCola.text = "Cola , $50"
            rbCola.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cola_1, 0)

            rbTea.text = "Tea , $60"
            rbTea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.blacktea, 0)

            rbCoffee.text = "Coffee , $100"
            rbCoffee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.latte, 0)
        } else {
            rbCola.text = "Ice cream , $150"
            rbCola.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icecream, 0)

            rbTea.text = "Milk tea , $100"
            rbTea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.milk_tea_icon, 0)

            rbCoffee.text = "Milk , $80"
            rbCoffee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.milk_icon, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rgDrinks = findViewById<RadioGroup>(R.id.rgDrinks)

        rbCola = findViewById<RadioButton>(R.id.rbCola)
        rbTea = findViewById<RadioButton>(R.id.rbTea)
        rbCoffee = findViewById<RadioButton>(R.id.rbCoffee)

        cbWaffle = findViewById<CheckBox>(R.id.cbWaffle)
        cbPancake = findViewById<CheckBox>(R.id.cbPancake)
        cbMuffin = findViewById<CheckBox>(R.id.cbMuffin)
        etWaffleQty = findViewById<EditText>(R.id.etWaffleQty)
        etPancakeQty = findViewById<EditText>(R.id.etPancakeQty)
        etMuffinQty = findViewById<EditText>(R.id.etMuffinQty)
        etDrinkQty = findViewById<EditText>(R.id.etDrinkQty)


        btnCheckout = findViewById<Button>(R.id.btnCheckout)
        btnCancel = findViewById<Button>(R.id.btnCancel)
        btnDrink1 = findViewById<Button>(R.id.btnDrink1)
        btnDrink2 = findViewById<Button>(R.id.btnDrink2)

        tvResult = findViewById<TextView>(R.id.tvResult)

        // Initialize with Menu 1
        updateMenu(1)

        btnDrink1.setOnClickListener {

            updateMenu(1)
        }

        btnDrink2.setOnClickListener {
            updateMenu(2)
        }

        btnCancel.setOnClickListener {
            rbCola.isChecked = false
            rbTea.isChecked = false
            rbCoffee.isChecked = false
            cbWaffle.isChecked = false
            cbPancake.isChecked = false
            cbMuffin.isChecked = false
            etWaffleQty.text.clear()
            etPancakeQty.text.clear()
            etMuffinQty.text.clear()
            etDrinkQty.text.clear()
            tvResult.text = "Order details will appear here..."
        }

        btnCheckout.setOnClickListener {
            var total = 0
            val summary = StringBuilder("Order Details:\n")
            
            val drinkQtyText = etDrinkQty.text.toString()
            val drinkQty = if (drinkQtyText.isEmpty()) 1 else drinkQtyText.toIntOrNull() ?: 1
            
            if (rbCola.isChecked) {
                val unitPrice = if (drinkNum == 1) 50 else 150
                val name = if (drinkNum == 1) "Cola" else "Ice cream"
                val price = unitPrice * drinkQty
                total += price
                summary.append("- $name ($$unitPrice) x $drinkQty = $$price\n")
            }
            if (rbTea.isChecked) {
                val unitPrice = if (drinkNum == 1) 60 else 100
                val name = if (drinkNum == 1) "Tea" else "Milk tea"
                val price = unitPrice * drinkQty
                total += price
                summary.append("- $name ($$unitPrice) x $drinkQty = $$price\n")
            }
            if (rbCoffee.isChecked) {
                val unitPrice = if (drinkNum == 1) 100 else 80
                val name = if (drinkNum == 1) "Coffee" else "Milk"
                val price = unitPrice * drinkQty
                total += price
                summary.append("- $name ($$unitPrice) x $drinkQty = $$price\n")
            }

            if (cbWaffle.isChecked) {
                val qty = etWaffleQty.text.toString().toIntOrNull() ?: 0
                val price = 100 * qty
                total += price
                summary.append("- Waffle ($100) x $qty = $$price\n")
            }
            if (cbPancake.isChecked) {
                val qty = etPancakeQty.text.toString().toIntOrNull() ?: 0
                val price = 120 * qty
                total += price
                summary.append("- Pancake ($120) x $qty = $$price\n")
            }
            if (cbMuffin.isChecked) {
                val qty = etMuffinQty.text.toString().toIntOrNull() ?: 0
                val price = 80 * qty
                total += price
                summary.append("- Muffin ($80) x $qty = $$price\n")
            }

            summary.append("\nTotal: $$total")
            tvResult.text = summary.toString()
        }
    }
}
