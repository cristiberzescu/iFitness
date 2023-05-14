package com.example.ifitness.application

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.util.*

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
        FoodCharacteristics.setdate(todayDate)

        var totalCalories = 0
        var totalProtein = 0F
        var totalCarbs = 0F
        var totalFats = 0F

        val emptyListMessage = findViewById(R.id.tv_empty_list) as TextView
        val totalCaloriesTextView = findViewById<TextView>(R.id.total_calories_text_view)
        val totalProteinTextView = findViewById<TextView>(R.id.total_protein_text_view)
        val totalCarbsTextView = findViewById<TextView>(R.id.total_carbs_text_view)
        val totalFatsTextView = findViewById<TextView>(R.id.total_fats_text_view)
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
        buttonNewFood.setOnClickListener {
            val intent = Intent(this, CreateFoodActivity::class.java)
            startActivity(intent)
        }
        buttonAddFood.setOnClickListener {
            val intent = Intent(this, SelectFoodActivity::class.java)
            startActivity(intent)
        }

        serviceRecycerView = findViewById(R.id.exercise_list)
        serviceRecycerView.layoutManager = LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)

        serviceArrayList = arrayListOf<Food>()

        fun addTotalsToFirebase(
            day: String,
            totalCalories: Int,
            totalProtein: Float,
            totalCarbs: Float,
            totalFats: Float
        ) {
            val userRef = FirebaseDatabase.getInstance().getReference("users")
                .child(UserCharacteristics.getUsername().toString())
            val totalsRef = userRef.child("totals").child(day)

            totalsRef.child("totalCalories").setValue(totalCalories)
            totalsRef.child("totalProtein").setValue(totalProtein)
            totalsRef.child("totalCarbs").setValue(totalCarbs)
            totalsRef.child("totalFats").setValue(totalFats)
        }

        fun getFoodData(day: String) {
            database = FirebaseDatabase.getInstance().getReference("users")
                .child(UserCharacteristics.getUsername().toString()).child("calories").child(day)

            database.orderByChild("currentTime").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (serviceSnapshot in snapshot.children) {
                            val food = serviceSnapshot.getValue(Food::class.java)
                            serviceArrayList.add(food!!)
                        }
                        serviceRecycerView.adapter = FoodListAdapter(serviceArrayList)
                        for (food in serviceArrayList) {
                            totalCalories += food.calories
                            totalProtein += food.protein
                            totalCarbs += food.carbs
                            totalFats += food.fats
                        }
                        totalCaloriesTextView.text = "Calories for $day: $totalCalories"
                        totalProteinTextView.text = "Total Protein: %.1f".format(totalProtein)
                        totalCarbsTextView.text = "Total Carbs: %.1f".format(totalCarbs)
                        totalFatsTextView.text = "Total Fats: %.1f".format(totalFats)

                        addTotalsToFirebase(day, totalCalories, totalProtein, totalCarbs, totalFats)
                    } else {
                        emptyListMessage.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        getFoodData(todayDate)
    }
}
