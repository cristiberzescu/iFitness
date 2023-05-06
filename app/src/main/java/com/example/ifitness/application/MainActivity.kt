package com.example.ifitness.application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val todayDate = String.format("%02d-%02d-%d", day, month + 1, year)

        database = FirebaseDatabase.getInstance().getReference("users")
            .child(UserCharacteristics.getUsername().toString()).child("totals").child(todayDate)

        var buttonBack = findViewById(R.id.btn_back) as Button
        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonWorkouts = findViewById(R.id.workouts_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton

        var userName = findViewById<TextView>(R.id.user_name)
        var userCalories = findViewById<TextView>(R.id.user_calories)


        userName.text = "Welcome, ${UserCharacteristics.getUsername().toString()}!"

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Obține valoarea câmpului "totalCalories"
                val totalCalories = dataSnapshot.child("totalCalories").getValue(Double::class.java)

                // Utilizează valoarea obținută
                if (totalCalories.toString() == "null") {
                    userCalories.text = "You have no foods added yet"
                } else {
                    userCalories.text = totalCalories.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Tratează erorile în cazul în care citirea datelor a fost anulată sau a eșuat
            }
        })


        buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonCalories.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }
        buttonWorkouts.setOnClickListener {
            val intent = Intent(this, SelectMuscleGroupActivity::class.java)
            startActivity(intent)
        }
        buttonTracking.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        buttonMap.setOnClickListener {
            val query = "gym Timisoara"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

    }
}