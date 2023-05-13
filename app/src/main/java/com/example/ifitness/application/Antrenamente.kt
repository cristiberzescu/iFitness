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
import com.example.ifitness.domain.*
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class Antrenamente : ComponentActivity() {
    var workout: Workout? = null
    var newWorkout: Workout? = null
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

    // Register the activity result contract and handle the result in a callback
    val resultLauncher = registerForActivityResult(myActivityResultContract) { result ->
        // Handle the result here
        Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()

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

        var exerciseList = arrayListOf<Exercise>()
        var serieses = arrayListOf<Series>()


        //val exerciseName = intent.getStringExtra("exerciseName")
        val bundle = intent.extras
        if (bundle != null) {
            workout = bundle.getParcelable("workout")
            if (workout == null) {
                workout = Workout("test", "15-2-2022", arrayListOf())
            }
            //Toast.makeText(this, workout!!.exercises!!.size, Toast.LENGTH_SHORT).show()
        } else {
            if (workout == null) {
                workout = Workout("test", "15-2-2022", arrayListOf())
            }
        }


//        if (exerciseName != null) {
//            Toast.makeText(this, "$exerciseName", Toast.LENGTH_SHORT).show()
//            var exercise = Exercise(exerciseName, serieses)
//            exerciseList.add(exercise)
//            newWorkout = Workout("piept", "06-05-2023", exerciseList)
//        }


        //Toast.makeText(this, "$exerciseName", Toast.LENGTH_SHORT).show()


        addExercise.setOnClickListener {


// Start the activity for result using the activity result contract
            resultLauncher.launch(null)

//            val intent = Intent(this, SelectExerciseActivity::class.java)
//            if (bundle != null) {
//                intent.putExtras(bundle)
//            }
//            startActivity(intent)
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
// Add the new item to the list
            //listRef.push().setValue(newItem)

//            val listRef =
//                database.child(UserCharacteristics.getUsername().toString()).child("workouts")
//            //listRef.push().setValue(workout)
//

// Attach a listener to the reference of the list in the database
//            listRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // Get the current list from the dataSnapshot
//                    val currentList = mutableListOf<Workout>()
//                    for (itemSnapshot in dataSnapshot.children) {
//                        val item = itemSnapshot.getValue(Workout::class.java)
//                        item?.let { currentList.add(it) }
//                    }
//
//                    // Update the list
//                    currentList.add(workout!!)
//
//                    // Set the updated value of the list in the database
//                    listRef.setValue(currentList)
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Handle errors
//                }
//            })


        }
        if (workout != null) {
            exerciseRecyclerView = findViewById(R.id.exercise_list)
            exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
            exerciseRecyclerView.setHasFixedSize(true)
            exerciseAdapter =
                ExerciseListAdapter(workout?.exercises as ArrayList<Exercise>, applicationContext )
            exerciseRecyclerView.adapter = exerciseAdapter
            //ExerciseListAdapter(workout?.exercises as ArrayList<Exercise>, applicationContext)
        }


//        if (newWorkout != null) {
//            exerciseRecyclerView = findViewById(R.id.exercise_list)
//            exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
//            exerciseRecyclerView.setHasFixedSize(true)
//
//            exerciseRecyclerView.adapter =
//                ExerciseListAdapter(
//                    newWorkout?.exercises as ArrayList<Exercise>,
//                    applicationContext
//                )
//        }


    }
}



