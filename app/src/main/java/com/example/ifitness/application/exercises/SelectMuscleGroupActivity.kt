package com.example.ifitness.application.exercises

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.application.main.MainActivity
import com.example.ifitness.application.tracking.TrackingActivity
import com.example.ifitness.application.calories.CaloriesActivity

class SelectMuscleGroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_muscle_group)

        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton

        var chestButton = findViewById(R.id.chest_button) as Button
        var backMuscleButton = findViewById(R.id.back_button) as Button
        var shouldersButton = findViewById(R.id.shoulders_button) as Button
        var armsButton = findViewById(R.id.arms_button) as Button
        var legsButton = findViewById(R.id.legs_button) as Button
        var absButton = findViewById(R.id.abs_button) as Button


        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonCalories.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
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
        val bundle = Bundle()

        chestButton.setOnClickListener {
            bundle.putString("muscleGroup", "chest")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
        backMuscleButton.setOnClickListener {
            bundle.putString("muscleGroup", "back")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
        shouldersButton.setOnClickListener {
            bundle.putString("muscleGroup", "shoulders")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
        armsButton.setOnClickListener {
            bundle.putString("muscleGroup", "arms")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
        absButton.setOnClickListener {
            bundle.putString("muscleGroup", "abs")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
        legsButton.setOnClickListener {
            bundle.putString("muscleGroup", "legs")

            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }
}
