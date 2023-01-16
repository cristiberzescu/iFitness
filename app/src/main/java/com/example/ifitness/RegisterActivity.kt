package com.example.ifitness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        database = Firebase.database.reference

        var toast: Toast = Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT)

        var userName = findViewById(R.id.et_user_name) as EditText
        var userEmail = findViewById(R.id.et_user_email) as EditText
        var userPassword = findViewById(R.id.et_password) as EditText
        var userConfirmPassword = findViewById(R.id.et_confirm_password) as EditText
        var buttonRegister = findViewById(R.id.btn_register) as Button
        var buttonBack = findViewById(R.id.btn_back) as Button

        fun newUser(){
            val email = userEmail.text.toString()
            val user_name = userName.text.toString()
            val password = userPassword.text.toString()
            val confirmpassword = userConfirmPassword.text.toString()

            if (email.isEmpty()) {
                userEmail.error = "Please enter a valid email"
            }

            if (user_name.isEmpty()) {
                userName.error = "Please enter user name"
            }

            if (password.isEmpty()) {
                userPassword.error = "Please enter a valid password"
            }

            if (confirmpassword.isEmpty()) {
                userConfirmPassword.error = "Please enter a matching password"
            }

//            Password must contain at least one digit [0-9].
//            Password must contain at least one lowercase Latin character [a-z].
//            Password must contain at least one uppercase Latin character [A-Z].
//            Password must contain at least one special character like ! @ # & ( ).
//            Password must contain a length of at least 8 characters and a maximum of 20 characters.

            if (!password.matches("^(?=.*[A-Z,a-z,\\d,!@#&()–[{}]:;',.?/*~\$^+=<>]).{6,20}\$".toRegex()))
                Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
            else

                if (password != confirmpassword && password.isNotEmpty())
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
                else {

                    val userId = database.push().key!!


                    val user = User(email, user_name, password)

                    database.child("users").child(userId).setValue(user)
                        .addOnCompleteListener {
                            if (user_name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                                Toast.makeText(
                                    this,
                                    "Data inserted successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                userName.text.clear()
                                userEmail.text.clear()
                                userPassword.text.clear()
                                userConfirmPassword.text.clear()
                            } else
                                Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()


                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            newUser()
        }
    }
}