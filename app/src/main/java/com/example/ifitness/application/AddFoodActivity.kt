package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.util.*


class AddFoodActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food_activity)

        database = FirebaseDatabase.getInstance().getReference("users")

        var btn_back = findViewById<Button>(R.id.btn_back)
        var btn_add_food = findViewById<Button>(R.id.btn_add_food)

        var food_name = findViewById(R.id.firebase_food_name) as TextView
        var food_calories = findViewById(R.id.firebase_food_calories) as TextView
        var food_protein = findViewById(R.id.firebase_food_protein) as TextView
        var food_carbs = findViewById(R.id.firebase_food_carbs) as TextView
        var food_fats = findViewById(R.id.firebase_food_fats) as TextView

        var food_grams = findViewById(R.id.food_grams) as EditText


        food_name.text = FoodCharacteristics.getName()
        food_calories.text = FoodCharacteristics.getCalories()
        food_protein.text = FoodCharacteristics.getProtein()
        food_carbs.text = FoodCharacteristics.getCarbs()
        food_fats.text = FoodCharacteristics.getFats()

//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val todayDate = String.format("%02d-%02d-%d", day, month + 1, year)
        val todayDate = FoodCharacteristics.getdate()

        fun addFood() {
            val foodName = food_name.text.toString()
            val foodCalories = food_calories.text.toString()
            val foodProtein = food_protein.text.toString()
            val foodCarbs = food_carbs.text.toString()
            val foodFat = food_fats.text.toString()
            val foodGrams = food_grams.text.toString()

            if ((!(foodGrams.matches("^[0-9]*$".toRegex()))) || foodGrams.isEmpty()) {
                food_grams.error = "Please enter valid grams"
            } else {
                val grams: Float = foodGrams.toFloat() / 100
                val currentTime = System.currentTimeMillis()
                val food = Food(
                    foodName,
                    foodGrams.toInt() * foodCalories.toInt() / 100,
                    grams * foodProtein.toFloat(),
                    grams * foodCarbs.toFloat(),
                    grams * foodFat.toFloat(),
                    foodGrams.toInt(),
                    currentTime
                )
                val id = UUID.randomUUID().toString()


                database.child(UserCharacteristics.getUsername().toString()).child("calories")
                    .child(todayDate.toString()).child(id).setValue(food)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG)
                            .show()

                        food_grams.text.clear()
                        FoodCharacteristics.setName("")
                        val intent = Intent(this, CaloriesActivity::class.java)
                        startActivity(intent)

                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }

        btn_add_food.setOnClickListener {
            addFood()
        }

        btn_back.setOnClickListener {
            FoodCharacteristics.setName("")
            val intent = Intent(this, SelectFoodActivity::class.java)
            startActivity(intent)
        }
    }
}
