package com.example.ifitness.application.tracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.application.calories.CaloriesActivity

class SelectExerciseActivity : ComponentActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private val initialData = mutableListOf(
        "Barbell Chest Press",
        "Elbow Lift Reverse Push Up",
        "Dumbbell Bench Press",
        "Lever Lying Chest Press",
        "Incline Barbell Bench Press",
        "Pull Downs",
        "Cable Pushdowns",
        "Assisted Pull Up",
        "Barbell Bent Over Row",
        "Barbell Seated",
        "Behind Neck Lat Pull Down",
        "Bent Over Row Resistance Band",
        "Cable One Arm Lat Pulldown",
        "Cable Seated On Floor Row",
        "Chin Up Reverse Normal Grip",
        "Exercise Ball Back Extension",
        "Inverted Rows On Smith Machine",
        "Supine Push Up",
        "Seated Bent-Over Lateral Raise",
        "Superman Pulls",
        "Plate-Loaded Lateral Raise",
        "Barbell Sumo Deadlift",
        "Kettlebell Sumo Deadlift",
        "Resistance Band Fitness",
        "Trap Bar Deadlift",
        "Seated Plate Load Calf Raise",
        "Chin Up Reverse Normal Grip",
        "Barbell Curl",
        "Dumbbell Hammer Curl",
        "Tricep Pushdown",
        "Dips",
        "Skull Crushers",
        "Close Grip Bench Press",
        "Cable Curls",
        "Cable Tricep Extension",
        "Preacher Curl",
        "Overhead Tricep Extension",
        "Concentration Curl"
    )
    private val filteredData = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_exercise)

        var searchEditText = findViewById(R.id.search_edittext) as EditText
        var searchListView = findViewById(R.id.search_listview) as ListView
        var btnBack = findViewById(R.id.btn_back) as Button

        btnBack.setOnClickListener {
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredData)
        searchListView.adapter = adapter

        searchListView.setOnItemClickListener { _, _, position, _ ->
            val result = adapter.getItem(position).toString()
            val intent2 = Intent()
            intent2.putExtra("result", result)
            setResult(Activity.RESULT_OK, intent2)
            finish()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                filterData(searchText)
                adapter.notifyDataSetChanged()
            }
        })

        filteredData.addAll(initialData)
    }

    private fun filterData(searchText: String) {
        filteredData.clear()
        if (searchText.isEmpty()) {
            filteredData.addAll(initialData)
        } else {
            filteredData.addAll(initialData.filter { it.contains(searchText, ignoreCase = true) })
        }
    }
}

