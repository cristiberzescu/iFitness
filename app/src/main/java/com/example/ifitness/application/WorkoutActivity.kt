package com.example.ifitness.application

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
import com.example.ifitness.adaptors.VideoListAdapter
import com.example.ifitness.domain.VideoExercise
import com.google.firebase.database.*

class WorkoutActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var serviceRecycerView: RecyclerView
    private lateinit var serviceArrayList: ArrayList<VideoExercise>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_activity)

        serviceRecycerView = findViewById(R.id.exerciseList)
        serviceRecycerView.layoutManager = LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)
        serviceArrayList = arrayListOf<VideoExercise>()

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
            muscleGroupName.text = bundle.getString("muscleGroup").toString()
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
                        serviceArrayList.add(exercise!!)
                    }
                    serviceRecycerView.adapter = VideoListAdapter(serviceArrayList)
                } else {
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WorkoutsActivity", "Failed to read value.", error.toException())
            }
        })
    }
}
