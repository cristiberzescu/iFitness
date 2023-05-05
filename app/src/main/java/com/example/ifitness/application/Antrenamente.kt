package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.ExerciseListAdapter
import com.example.ifitness.domain.Exercise
import com.example.ifitness.domain.Workout

class Antrenamente : ComponentActivity() {
    var workout: Workout? = null
    private lateinit var exerciseRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.antrenamente)

        var addExercise = findViewById(R.id.add_exercise_button) as Button
        var backButton = findViewById(R.id.back_button) as Button
        var saveButton = findViewById(R.id.save_button) as Button

        val bundle=intent.extras
        if(bundle!=null){
            workout=bundle.getParcelable("workout")
        }


        addExercise.setOnClickListener {
            val intent = Intent(this, SelectExerciseActivity::class.java)
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        saveButton.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        if(workout!=null) {
            exerciseRecyclerView = findViewById(R.id.exercise_list)
            exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
            exerciseRecyclerView.setHasFixedSize(true)

            exerciseRecyclerView.adapter =
                ExerciseListAdapter(workout?.exercises as ArrayList<Exercise>, applicationContext)
        }


    }
}

