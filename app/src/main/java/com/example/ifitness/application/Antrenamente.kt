package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.ExerciseListAdapter
import com.example.ifitness.domain.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class Antrenamente : ComponentActivity() {
    var workout: Workout? = null
    var newWorkout: Workout? = null
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.antrenamente)

        database = FirebaseDatabase.getInstance().getReference("users")

        var addExercise = findViewById(R.id.add_exercise_button) as Button
        var backButton = findViewById(R.id.back_button) as Button
        var saveButton = findViewById(R.id.save_button) as Button

        var exerciseList = arrayListOf<Exercise>()
        var serieses = arrayListOf<Series>()

        //val exerciseName = intent.getStringExtra("exerciseName")
        val bundle = intent.extras
        if (bundle != null) {
            workout = bundle.getParcelable("workout")
            //Toast.makeText(this, "nu e null", Toast.LENGTH_SHORT).show()
        }

//        if (exerciseName != null) {
//            Toast.makeText(this, "$exerciseName", Toast.LENGTH_SHORT).show()
//            var exercise = Exercise(exerciseName, serieses)
//            exerciseList.add(exercise)
//            newWorkout = Workout("piept", "06-05-2023", exerciseList)
//        }


        //Toast.makeText(this, "$exerciseName", Toast.LENGTH_SHORT).show()


        addExercise.setOnClickListener {
            val intent = Intent(this, SelectExerciseActivity::class.java)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        saveButton.setOnClickListener {
            val id = UUID.randomUUID().toString()
            database.child(UserCharacteristics.getUsername().toString()).child("workouts")
                .child(id).setValue(workout)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG)
                        .show()

                    val intent = Intent(this, TrackingActivity::class.java)
                    startActivity(intent)

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }


        }
        if (workout != null) {
            exerciseRecyclerView = findViewById(R.id.exercise_list)
            exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
            exerciseRecyclerView.setHasFixedSize(true)

            exerciseRecyclerView.adapter =
                ExerciseListAdapter(workout?.exercises as ArrayList<Exercise>, applicationContext)
        }


        if (newWorkout != null) {
            exerciseRecyclerView = findViewById(R.id.exercise_list)
            exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
            exerciseRecyclerView.setHasFixedSize(true)

            exerciseRecyclerView.adapter =
                ExerciseListAdapter(
                    newWorkout?.exercises as ArrayList<Exercise>,
                    applicationContext
                )
        }


    }
}

