package com.example.ifitness.application.tracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.ifitness.R
import com.example.ifitness.domain.*

class SelectExerciseActivity : ComponentActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchListView: ListView
    var workout: Workout? = null

    private val data = mutableListOf(
        "impins la piept",
        "fluturari",
        "genoflexiuni",
        "tractiuni",
        "flexii biceps",
        "extensii triceps",
        "indreptari"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_exercise)

        searchEditText = findViewById(R.id.search_edittext)
        searchListView = findViewById(R.id.search_listview)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        searchListView.adapter = adapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredData = data.filter { it.contains(s.toString(), ignoreCase = true) }
                adapter.clear()
                adapter.addAll(filteredData)
                adapter.notifyDataSetChanged()


                var exerciseList = arrayListOf<Exercise>()
                val serieses = arrayListOf<Series>()
                val bundle = intent.extras
                if (bundle != null) {
                    workout = bundle.getParcelable("workout")
                }

                searchListView.setOnItemClickListener { _, _, position, _ ->
                    val selectedExercise = adapter.getItem(position)
                    val exercise = Exercise(selectedExercise.toString(), serieses)
                    exerciseList.add(exercise)
                    workout = Workout("chest", "06-05-2023", exerciseList)


                    val result = selectedExercise.toString()
                    val intent2 = Intent()
                    intent2.putExtra("result", result)
                    setResult(Activity.RESULT_OK, intent2)
                    finish()
                }


            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}



