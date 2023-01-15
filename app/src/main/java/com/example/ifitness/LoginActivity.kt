package com.example.ifitness

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        var toast: Toast = Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT)

        var userEmail = findViewById(R.id.et_user_email) as EditText
        var userPassword = findViewById(R.id.et_password) as EditText
        var buttonLogin = findViewById(R.id.btn_login) as Button
        var buttonRegister = findViewById(R.id.btn_register) as Button
        buttonLogin.setOnClickListener {
            if (userEmail.text.toString().isEmpty()||userPassword.text.toString().isEmpty()) {
                Handler().postDelayed({
                        toast.show()
                }, 250)
            }
            else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}