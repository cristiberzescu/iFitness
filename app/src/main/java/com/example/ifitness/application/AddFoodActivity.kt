package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.google.firebase.database.*


class AddFoodActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var foodRecycerView: RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food_activity)

        var btn_back = findViewById<Button>(R.id.btn_back)
        var btn_add_food = findViewById<Button>(R.id.btn_add_food)


        var food_name = findViewById(R.id.firebase_food_name) as TextView
        var food_grams = findViewById(R.id.food_grams) as EditText

        food_name.text=FoodCharacteristics.getTitle()

        btn_add_food.setOnClickListener {
            FoodCharacteristics.setTitle("")
            // addNewFood()
        }

        btn_back.setOnClickListener {
            FoodCharacteristics.setTitle("")
            val intent = Intent(this, SelectFoodActivity::class.java)
            startActivity(intent)
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


        foodArrayList = arrayListOf<Food>()

    }

    private fun getFoodData(text: String) {
        database = FirebaseDatabase.getInstance().getReference("foods")
        foodArrayList.clear()
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (foodSnapshot in snapshot.children) {
                        val foodNameTest = foodSnapshot.child("name").value.toString()
                        if (foodNameTest.lowercase().contains(text.lowercase())) {
                            val food = foodSnapshot.getValue(Food::class.java)
                            foodArrayList.add(food!!)
                        }
                    }
                    foodRecycerView.adapter = FoodListAdapter(foodArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Trateaza cazurile de eroare
            }
        })
    }

}


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