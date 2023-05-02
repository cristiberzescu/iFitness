package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.ExerciseListAdapter
import com.example.ifitness.domain.VideoExercise
import com.google.firebase.database.*

class WorkoutActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var videoView: VideoView
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
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val bundle=intent.extras

        if (bundle != null) {
            muscleGroupName.text=bundle.getString("muscleGroup").toString()
        }


        if (bundle != null) {
            database = FirebaseDatabase.getInstance().getReference("exercises").child(bundle.getString("muscleGroup")
                .toString())

        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val exercise = serviceSnapshot.getValue(VideoExercise::class.java)
                        serviceArrayList.add(exercise!!)
                    }
                    serviceRecycerView.adapter = ExerciseListAdapter(serviceArrayList)


                } else {
                    // Dacă lista este goală, faceți TextView-ul vizibil.

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WorkoutsActivity", "Failed to read value.", error.toException())
            }
        })


    }
}


//
//        videoView = findViewById(R.id.video_view)
//
//        database = FirebaseDatabase.getInstance().getReference("exercises").child("chest")
//            .child("bench press").child("url")
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val url = snapshot.getValue(String::class.java)
//                url?.let {
//                    videoView.setVideoPath(url)
//                    videoView.start()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("WorkoutsActivity", "Failed to read value.", error.toException())
//            }
//        })