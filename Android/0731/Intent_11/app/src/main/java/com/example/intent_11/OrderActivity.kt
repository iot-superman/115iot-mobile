package com.example.intent_11

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {
    val orderCode = 100
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
    private lateinit var ivDrink1: ImageView
    private lateinit var ivDrink2: ImageView
    private lateinit var ivDrink3: ImageView
    
    private var drinkNum = 1

    private fun updateMenu(menuNum: Int) {
        rgDrinks.clearCheck()
        drinkNum = menuNum
        if (menuNum == 1) {
            rbCola.text = "Cola , $50"
            ivDrink1.setImageResource(R.drawable.cola_1)

            rbTea.text = "Tea , $60"
            ivDrink2.setImageResource(R.drawable.blacktea)

            rbCoffee.text = "Coffee , $100"
            ivDrink3.setImageResource(R.drawable.latte)
        } else {
            rbCola.text = "Ice cream , $150"
            ivDrink1.setImageResource(R.drawable.icecream)

            rbTea.text = "Milk tea , $100"
            ivDrink2.setImageResource(R.drawable.milk_tea_icon)

            rbCoffee.text = "Milk , $80"
            ivDrink3.setImageResource(R.drawable.milk_icon)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
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

        ivDrink1 = findViewById(R.id.ivDrink1)
        ivDrink2 = findViewById(R.id.ivDrink2)
        ivDrink3 = findViewById(R.id.ivDrink3)


        btnCheckout = findViewById<Button>(R.id.btnCheckout)
        btnCancel = findViewById<Button>(R.id.btnCancel)
        btnDrink1 = findViewById<Button>(R.id.btnDrink1)
        btnDrink2 = findViewById<Button>(R.id.btnDrink2)

        tvResult = findViewById<TextView>(R.id.tvResult)

        // Add TextWatchers for auto-check logic
        val dessertQtyWatcher = { editText: EditText, checkBox: CheckBox ->
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val qty = s.toString().toIntOrNull() ?: 0
                    checkBox.isChecked = qty >= 1
                }
            }
        }

        etWaffleQty.addTextChangedListener(dessertQtyWatcher(etWaffleQty, cbWaffle))
        etPancakeQty.addTextChangedListener(dessertQtyWatcher(etPancakeQty, cbPancake))
        etMuffinQty.addTextChangedListener(dessertQtyWatcher(etMuffinQty, cbMuffin))

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
            if (!(rbCola.isChecked || rbTea.isChecked || rbCoffee.isChecked)) {
                Toast.makeText(this, "Please select at least one drink", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var total = 0
            val summary = StringBuilder("Your order is :\n")

            val drinkQtyText = etDrinkQty.text.toString()
            val drinkQty = if (drinkQtyText.isEmpty()) 1 else drinkQtyText.toIntOrNull() ?: 1

            if (rbCola.isChecked) {
                val unitPrice = if (drinkNum == 1) 50 else 150
                val name = if (drinkNum == 1) "Cola" else "Ice cream"
                val price = unitPrice * drinkQty
                total += price
                summary.append("Drink : $name -> $$unitPrice x $drinkQty = $price\n")
            }
            if (rbTea.isChecked) {
                val unitPrice = if (drinkNum == 1) 60 else 100
                val name = if (drinkNum == 1) "Tea" else "Milk tea"
                val price = unitPrice * drinkQty
                total += price
                summary.append("Drink : $name -> $$unitPrice x $drinkQty = $price\n")
            }
            if (rbCoffee.isChecked) {
                val unitPrice = if (drinkNum == 1) 100 else 80
                val name = if (drinkNum == 1) "Coffee" else "Milk"
                val price = unitPrice * drinkQty
                total += price
                summary.append("Drink : $name -> $$unitPrice x $drinkQty = $price\n")
            }

            summary.append("Dessert :\n")
            if (cbWaffle.isChecked) {
                val qty = etWaffleQty.text.toString().toIntOrNull() ?: 0
                val price = 100 * qty
                total += price
                summary.append("Waffle -> $100 x $qty = $price\n")
            }
            if (cbPancake.isChecked) {
                val qty = etPancakeQty.text.toString().toIntOrNull() ?: 0
                val price = 120 * qty
                total += price
                summary.append("Pancake -> 120 x $qty = $price\n")
            }
            if (cbMuffin.isChecked) {
                val qty = etMuffinQty.text.toString().toIntOrNull() ?: 0
                val price = 80 * qty
                total += price
                summary.append("Muffin -> 80 x $qty = $price\n")
            }

            summary.append("\nThe total fee is $$total")
            tvResult.text = summary.toString()

            val data = tvResult.text.toString()
            val intent = Intent()
            intent.putExtra("order", data)
            setResult(orderCode, intent)
            finish()

        }
    }
}
