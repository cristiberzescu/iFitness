package com.example.ifitness.application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.security.MessageDigest

class LoginActivity : ComponentActivity() {
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.login_activity)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("users")

        var count: Int? = 0
        var toast: Toast = Toast.makeText(
            applicationContext,
            "User name or Password incorrect!",
            Toast.LENGTH_SHORT
        )

        var name = findViewById(R.id.et_user_name) as EditText
        var password = findViewById(R.id.et_password) as EditText
        var buttonLogin = findViewById(R.id.btn_login) as Button
        var buttonRegister = findViewById(R.id.btn_register) as Button
        buttonLogin.setOnClickListener {

            fun encryptPassword(password: String): String {
                val digest = MessageDigest.getInstance("SHA-256")
                val bytes = digest.digest(password.toByteArray())
                return bytes.joinToString("") { "%02x".format(it) }
            }

            val intent = Intent(this, MainActivity::class.java)
            databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children) {

                        val userName = ds.child("userName").value.toString()
                        val userPassword = ds.child("userPassword").value.toString()
                        val userEmail = ds.child("userEmail").value.toString()

                        if (name.text.toString() == userName && encryptPassword(password.text.toString()) == userPassword) {

                            UserCharacteristics.setUsername(userName)
                            UserCharacteristics.setEmail(userEmail)
                            startActivity(intent)
                            toast.cancel()
                            count = 1
                        } else {
                            count = 0
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ooooo", "onCancelled: ${error.toException()}")
                }
            })

            Handler().postDelayed({
                if (count == 0) {
                    toast.show()
                }
            }, 150)

        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}