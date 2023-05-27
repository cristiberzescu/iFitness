package com.example.ifitness.application.exercises

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adapters.VideoListAdapter
import com.example.ifitness.application.main.MainActivity
import com.example.ifitness.application.tracking.TrackingActivity
import com.example.ifitness.application.calories.CaloriesActivity
import com.example.ifitness.domain.VideoExercise
import com.google.firebase.database.*

class WorkoutActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var exerciseRecycerView: RecyclerView
    private lateinit var exerciseArrayList: ArrayList<VideoExercise>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_activity)

        exerciseRecycerView = findViewById(R.id.exerciseList)
        exerciseRecycerView.layoutManager = LinearLayoutManager(this)
        exerciseRecycerView.setHasFixedSize(true)
        exerciseArrayList = arrayListOf<VideoExercise>()

        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonWorkouts = findViewById(R.id.workouts_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton
        var muscleGroupName = findViewById(R.id.muscleGroup) as TextView

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

        val bundle = intent.extras
        if (bundle != null) {
            muscleGroupName.text = bundle.getString("muscleGroup").toString().uppercase()
            database = FirebaseDatabase.getInstance().getReference("exercises").child(
                bundle.getString("muscleGroup")
                    .toString()
            )
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val exercise = serviceSnapshot.getValue(VideoExercise::class.java)
                        exerciseArrayList.add(exercise!!)
                    }
                    exerciseRecycerView.adapter = VideoListAdapter(exerciseArrayList)
                } else {
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WorkoutsActivity", "Failed to read value.", error.toException())
            }
        })
    }
}
