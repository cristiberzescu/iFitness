package com.example.ifitness.application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.ExerciseListAdapter
import com.example.ifitness.domain.Exercise
import com.example.ifitness.domain.Series
import com.example.ifitness.domain.UserCharacteristics
import com.example.ifitness.domain.Workout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Antrenamente : ComponentActivity() {
    var workout: Workout? = null
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var exerciseAdapter: ExerciseListAdapter

    val myActivityResultContract = object : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context, SelectExerciseActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getStringExtra("result")
            }
            return null
        }
    }

    val resultLauncher = registerForActivityResult(myActivityResultContract) { result ->
        Toast.makeText(this, "Add series", Toast.LENGTH_SHORT).show()

        val exercise = Exercise(result!!, arrayListOf<Series>())
        workout!!.exercises!!.add(exercise)
        exerciseAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.antrenamente)

        database = FirebaseDatabase.getInstance().getReference("users")

        var addExercise = findViewById(R.id.add_exercise_button) as Button
        var backButton = findViewById(R.id.back_button) as Button
        var saveButton = findViewById(R.id.save_button) as Button

        val bundle = intent.extras
        if (bundle != null) {
            workout = bundle.getParcelable("workout")
            if (workout == null) {
                workout = Workout("test", "15-2-2022", arrayListOf())
            }
        } else {
            if (workout == null) {
                workout = Workout("test", "15-2-2022", arrayListOf())
            }
        }

        addExercise.setOnClickListener {
            resultLauncher.launch(null)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        saveButton.setOnClickListener {
            val id = UUID.randomUUID().toString()
            database.child(UserCharacteristics.getUsername().toString()).child("workouts").child(id)
                .setValue(workout).addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

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
            exerciseAdapter = ExerciseListAdapter(workout?.exercises as ArrayList<Exercise>, this)
            exerciseRecyclerView.adapter = exerciseAdapter
        }
    }
}



