package com.example.ifitness.application

//import com.example.ifitness.database
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private var databaseReferenceUsers: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        database = Firebase.database.reference
        //databaseReferenceUsers = FirebaseDatabase.getInstance()?.getReference("Users")

        var userName = findViewById(R.id.et_user_name) as EditText
        var userEmail = findViewById(R.id.et_user_email) as EditText
        var userPassword = findViewById(R.id.et_password) as EditText
        var userConfirmPassword = findViewById(R.id.et_confirm_password) as EditText
        var buttonRegister = findViewById(R.id.btn_register) as Button
        var buttonBack = findViewById(R.id.btn_back) as Button

        var toast: Toast =
            Toast.makeText(applicationContext, "User already exist", Toast.LENGTH_SHORT)
        var count: Int? = 0

        buttonRegister.setOnClickListener {

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

//            Password can contain digits [0-9].
//            Password can contain lowercase Latin characters [a-z].
//            Password can contain uppercase Latin characters [A-Z].
//            Password can contain special characters like ! @ # & ( ).
//            Password must contain a length of at least 6 characters and a maximum of 20 characters.

            if (!password.matches("^(?=.*[A-Z,a-z,\\d,!@#&()–[{}]:;',.?/*~\$^+=<>]).{6,20}\$".toRegex()))
                Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
            else if (password != confirmpassword && password.isNotEmpty())
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            else if (user_name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {


                database.child("Users").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {

                            val firebaseUserName = ds.child("userName").value.toString()
                            val firebaseUserEmail = ds.child("userEmail").value.toString()

                            if (user_name == firebaseUserName || email == firebaseUserEmail) {
                                count = 1
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("ooooo", "onCancelled: ${error.toException()}")
                    }
                })
                if (count == 0) {
                    //val userId = database.push().key!!

                    val user = User(email, user_name, password)

                    database.child("Users").child(user_name).setValue(user)
                        .addOnCompleteListener {

                            Toast.makeText(
                                this,
                                "Data inserted successfully",
                                Toast.LENGTH_LONG
                            ).show()

                            userName.text.clear()
                            userEmail.text.clear()
                            userPassword.text.clear()
                            userConfirmPassword.text.clear()

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)

                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    toast.show()
                }

            } else Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()

        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}