package com.example.ifitness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var toast: Toast = Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT)

//        var userName = findViewById(R.id.et_user_name) as EditText
//        var userPassword = findViewById(R.id.et_password) as EditText
//        var buttonRegister = findViewById(R.id.btn_register) as Button
          var buttonBack = findViewById(R.id.btn_back) as Button
        buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

//        buttonRegister.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }

    }
}