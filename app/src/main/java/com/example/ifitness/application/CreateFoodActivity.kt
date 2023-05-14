package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.Food
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference
private var firebaseDatabase: FirebaseDatabase? = null

class CreateFoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_food_activity)

        database = Firebase.database.reference

        var btn_back = findViewById<Button>(R.id.btn_back)
        var btn_save_food = findViewById<Button>(R.id.btn_save_food)


        var food_name = findViewById(R.id.food_name) as EditText
        var food_calories = findViewById(R.id.food_calories) as EditText
        var food_protein = findViewById(R.id.food_protein) as EditText
        var food_carbs = findViewById(R.id.food_carbs) as EditText
        var food_fat = findViewById(R.id.food_fat) as EditText


        fun createNewFood() {
            val foodName = food_name.text.toString()
            val foodCalories = food_calories.text.toString()
            val foodProtein = food_protein.text.toString()
            val foodCarbs = food_carbs.text.toString()
            val foodFat = food_fat.text.toString()


            if (foodName.isEmpty()) {
                food_name.error = "Please enter a name"
            }

            if (foodCalories.isEmpty()) {
                food_calories.error = "Please enter calories"
            }

            if (foodProtein.isEmpty()) {
                food_protein.error = "Please enter protein"
            }
            if (foodCarbs.isEmpty()) {
                food_carbs.error = "Please enter carbs"
            }

            if (foodFat.isEmpty()) {
                food_fat.error = "Please enter fat"
            }

            if (!(foodCalories.matches("^[0-9]*$".toRegex()) && foodProtein.matches("^[0-9]+([,.][0-9]{1})?\$".toRegex()) && foodCarbs.matches(
                    "^[0-9]+([,.][0-9]{1})?\$".toRegex()
                ) && foodFat.matches("^[0-9]+([,.][0-9]{1})?\$".toRegex()))
            ) Toast.makeText(
                this, "fields not valid, you can use max 1 decimal", Toast.LENGTH_LONG
            ).show()
            else {
                val food = Food(
                    foodName,
                    foodCalories.toInt(),
                    foodProtein.replace(',', '.').toFloat(),
                    foodCarbs.replace(',', '.').toFloat(),
                    foodFat.replace(',', '.').toFloat(),
                    100
                )

                database.child("foods").child(foodName).setValue(food).addOnCompleteListener {
                    if (foodName.isNotEmpty() && foodCalories.isNotEmpty() && foodProtein.isNotEmpty() && foodCarbs.isNotEmpty() && foodFat.isNotEmpty()) {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG)
                            .show()

                        food_name.text.clear()
                        food_calories.text.clear()
                        food_protein.text.clear()
                        food_carbs.text.clear()
                        food_fat.text.clear()

                        val intent = Intent(this, CaloriesActivity::class.java)
                        startActivity(intent)
                    } else Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
        firebaseDatabase = FirebaseDatabase.getInstance()

        btn_save_food.setOnClickListener {
            createNewFood()
        }
        btn_back.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}