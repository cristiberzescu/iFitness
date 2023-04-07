package com.example.ifitness.application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class CaloriesActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var serviceRecycerView: RecyclerView
    private lateinit var serviceArrayList: ArrayList<Food>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calories_activity)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val todayDate = String.format("%02d-%02d-%d", day, month + 1, year)


        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonWorkouts = findViewById(R.id.workouts_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton
        var buttonNewFood = findViewById(R.id.btn_new_food) as Button
        var buttonAddFood = findViewById(R.id.btn_add_food) as Button

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonCalories.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }
        buttonWorkouts.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }
        buttonTracking.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
        buttonMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        buttonNewFood.setOnClickListener {
            val intent = Intent(this, CreateFoodActivity::class.java)
            startActivity(intent)
        }
        buttonAddFood.setOnClickListener {
            val intent = Intent(this, SelectFoodActivity::class.java)
            startActivity(intent)
        }

        serviceRecycerView = findViewById(R.id.foodList)
        serviceRecycerView.layoutManager = LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)

        serviceArrayList = arrayListOf<Food>()

        getFoodData(todayDate)

    }

    private fun getFoodData(day: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
            .child(UserCharacteristics.getUsername().toString()).child("calories").child(day)

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val food = serviceSnapshot.getValue(Food::class.java)
                        serviceArrayList.add(food!!)
                    }
                    serviceRecycerView.adapter = FoodListAdapter(serviceArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}


/*
// Adaugarea unui aliment sau a unei bauturi in baza de date
val food = Food("Banana", 105, "04/03/2023")
val database = FirebaseDatabase.getInstance().reference
val foodRef = database.child("users").child(userId).child("foods")
foodRef.push().setValue(food)

// Afisarea alimentelor si bauturilor intr-un RecyclerView
val foodList = ArrayList<Food>()
val foodAdapter = FoodAdapter(foodList)
val layoutManager = LinearLayoutManager(this)
recyclerView.layoutManager = layoutManager
recyclerView.adapter = foodAdapter

val database = FirebaseDatabase.getInstance().reference
val foodRef = database.child("users").child(userId).child("foods")
foodRef.addValueEventListener(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (snapshot in dataSnapshot.children) {
            val food = snapshot.getValue(Food::class.java)
            foodList.add(food)
        }
        foodAdapter.notifyDataSetChanged()
    }

    override fun onCancelled(databaseError: DatabaseError) {
        // Handle error
    }
})


// Selectarea datei cu ajutorul unui DatePicker
val calendar = Calendar.getInstance()
val year = calendar.get(Calendar.YEAR)
val month = calendar.get(Calendar.MONTH)
val day = calendar.get(Calendar.DAY_OF_MONTH)

val datePickerDialog = DatePickerDialog(this,
    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        // Salvarea datei selectate
        val date = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year)
    }, year, month, day)

datePickerDialog.show()

 */