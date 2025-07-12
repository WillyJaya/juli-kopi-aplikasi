package com.julikopi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val productBtn = findViewById<Button>(R.id.productListButton)
        val orderBtn = findViewById<Button>(R.id.orderButton)
        val historyBtn = findViewById<Button>(R.id.historyButton)

        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        productBtn.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        orderBtn.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        historyBtn.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}