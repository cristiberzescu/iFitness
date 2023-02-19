package com.example.ifitness

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
import com.google.firebase.database.*

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
        databaseReference = firebaseDatabase?.getReference("Users")

        var count: Int? = 0
        var toast: Toast = Toast.makeText(applicationContext, "User name or Password incorrect!", Toast.LENGTH_SHORT)

        var name = findViewById(R.id.et_user_name) as EditText
        var password = findViewById(R.id.et_password) as EditText
        var buttonLogin = findViewById(R.id.btn_login) as Button
        var buttonRegister = findViewById(R.id.btn_register) as Button
        buttonLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children) {

                        //val id = ds.key.toString()
                        val userName = ds.child("userName").value.toString()
                        val userPassword = ds.child("userPassword").value.toString()
                        val userEmail = ds.child("userEmail").value.toString()

                        if (name.text.toString() == userName && password.text.toString() == userPassword) {

                            //ProfileCharacteristics.setKey(id)
                            ProfileCharacteristics.setUsername(userName)
                            ProfileCharacteristics.setEmail(userEmail)
                            startActivity(intent)
                            toast.cancel()

                            count = 1

                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ooooo", "onCancelled: ${error.toException()}")
                }
            })

            Handler().postDelayed({
                if (count == 0) {
                    //Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
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