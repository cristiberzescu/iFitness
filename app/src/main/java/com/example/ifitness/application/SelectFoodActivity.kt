package com.example.ifitness.application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.google.firebase.database.*


class SelectFoodActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var foodRecycerView: RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>
    var contor: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_food_activity)

        var btn_back = findViewById<Button>(R.id.btn_back)

        var food_name = findViewById(R.id.food_name_search) as SearchView


        btn_back.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }

        food_name.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onQueryTextChange(newText: String): Boolean {

                getFoodData(newText)

                return false
            }

        })


        foodRecycerView = findViewById(R.id.foodList)
        foodRecycerView.layoutManager = LinearLayoutManager(this)
        foodRecycerView.setHasFixedSize(true)

        foodArrayList = arrayListOf<Food>()

    }

    fun getStatus() {
        if (contor == true) {
            val intent1 = Intent(this, AddFoodActivity::class.java)
            startActivity(intent1)
        }
    }

    fun onAddButtonClick() {
        val intent = Intent(this, AddFoodActivity::class.java)
        //intent.putExtra("foods", food)
        startActivity(intent)
    }

    private fun getFoodData(text: String) {
        database = FirebaseDatabase.getInstance().getReference("foods")
        foodArrayList.clear()

//        val listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (foodSnapshot in snapshot.children) {
//                        val foodNameTest = foodSnapshot.child("name").value.toString()
//                        if (foodNameTest.lowercase().contains(text.lowercase())) {
//                            val food = foodSnapshot.getValue(Food::class.java)
//                            foodArrayList.add(food!!)
//                        }
//                    }
//                    foodRecycerView.adapter = FoodListAdapter(foodArrayList)
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Trateaza cazurile de eroare
//            }
//
//        }
//        database.addListenerForSingleValueEvent(listener)
//        if (FoodCharacteristics.getTitle()?.isNotEmpty() == true) {
//            database.removeEventListener(listener)
//            onAddButtonClick()
//
//        }


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
                if(FoodCharacteristics.getName()?.isNotEmpty() == true){

                    onAddButtonClick()
                    return
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Trateaza cazurile de eroare
            }

        })
    }

}