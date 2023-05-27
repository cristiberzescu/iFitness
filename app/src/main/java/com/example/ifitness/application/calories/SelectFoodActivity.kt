package com.example.ifitness.application.calories

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adapters.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.google.firebase.database.*

class SelectFoodActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var foodRecycerView: RecyclerView
    private lateinit var foodArrayList: ArrayList<Food>
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
        foodRecycerView = findViewById(R.id.exercise_list)
        foodRecycerView.layoutManager = LinearLayoutManager(this)
        foodRecycerView.setHasFixedSize(true)
        foodArrayList = arrayListOf<Food>()
    }

    fun onAddButtonClick() {
        val intent = Intent(this, AddFoodActivity::class.java)
        startActivity(intent)
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
                if (FoodCharacteristics.getName()?.isNotEmpty() == true) {
                    onAddButtonClick()
                    return
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}
