package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class AddFoodActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food_activity)

        database = FirebaseDatabase.getInstance().getReference("Users")

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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val todayDate = String.format("%02d-%02d-%d", day, month + 1, year)

        fun addFood() {
            val foodName = food_name.text.toString()
            val foodCalories = food_calories.text.toString()
            val foodProtein = food_protein.text.toString()
            val foodCarbs = food_carbs.text.toString()
            val foodFat = food_fats.text.toString()
            val foodGrams = food_grams.text.toString()

            if (!(foodGrams.matches("^[0-9]*$".toRegex())) || foodGrams.isEmpty()) {
                food_grams.error = "Please enter valid grams"
            } else {

                val food = Food(
                    foodName,
                    foodCalories.toInt(),
                    foodProtein.toInt(),
                    foodCarbs.toInt(),
                    foodFat.toInt(),
                    foodGrams.toInt()
                )



                database.child(UserCharacteristics.getUsername().toString()).child("calories").child(todayDate).child(foodName).setValue(food)
                    .addOnCompleteListener {
                        if (foodGrams.isNotEmpty()) {
                            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG)
                                .show()

                            food_grams.text.clear()

                            FoodCharacteristics.setName("")


                            val intent = Intent(this, CaloriesActivity::class.java)
                            startActivity(intent)
                        } else Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()


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

//        food_name.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//
//                getFoodData(newText)
//                return false
//            }
//
//        })


//           if(foodNameData.lowercase()?.contains(text.lowercase())==true)

//        fun addNewFood() {
//            val foodName = food_name.text.toString()
//            val foodGrams = food_grams.text.toString()
//
//
//            if (foodName.isEmpty()) {
//                food_name.error = "Please enter a name"
//            }
//
//            if (foodGrams.isEmpty()) {
//                food_grams.error = "Please enter grams"
//            }
//
//            if (!(foodGrams.matches("^[0-9]*$".toRegex()))
//            ) Toast.makeText(
//                this, "fields not valid", Toast.LENGTH_LONG
//            ).show()
//            else {
//                val food = Food(
//                    foodName,
//                    foodGrams.toInt()
//                )
//
//                database.child("foods").child(foodName).setValue(food).addOnCompleteListener {
//                    if (foodName.isNotEmpty() && foodGrams.isNotEmpty()) {
//                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG)
//                            .show()
//
//                        food_name.text.clear()
//                        food_grams.text.clear()
//
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                    } else Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()
//
//
//                }.addOnFailureListener { err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                }
//
//
//            }
//        }


//
//private fun getFoodData(text: String) {
//    database = FirebaseDatabase.getInstance().getReference("foods")
//    foodArrayList.clear()
//    database.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            if (snapshot.exists()) {
//                for (foodSnapshot in snapshot.children) {
//                    val foodNameTest = foodSnapshot.child("name").value.toString()
//                    if (foodNameTest.lowercase().contains(text.lowercase())) {
//                        val food = foodSnapshot.getValue(Food::class.java)
//                        foodArrayList.add(food!!)
//                    }
//                }
//                foodRecycerView.adapter = FoodListAdapter(foodArrayList)
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            // Trateaza cazurile de eroare
//        }
//    })
//}