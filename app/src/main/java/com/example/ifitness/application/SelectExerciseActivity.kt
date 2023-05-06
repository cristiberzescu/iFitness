package com.example.ifitness.application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics
import com.google.firebase.database.*

class SelectExerciseActivity : ComponentActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchListView: ListView

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

        // Inițializarea referințelor la view-uri
        searchEditText = findViewById(R.id.search_edittext)
        searchListView = findViewById(R.id.search_listview)

        // Crearea adaptorului de date
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        searchListView.adapter = adapter

        // Adăugarea unui text watcher pentru EditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nu este necesară implementarea acestei metode
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrarea datelor în funcție de textul introdus în EditText
                val filteredData = data.filter { it.contains(s.toString(), ignoreCase = true) }

                // Actualizarea adaptorului de date cu datele filtrate
                adapter.clear()
                adapter.addAll(filteredData)
                adapter.notifyDataSetChanged()

                // Adaugarea unui listener pentru ListView


                searchListView.setOnItemClickListener { _, _, position, _ ->
                    val selectedExercise = adapter.getItem(position)
                    val intent = Intent(this@SelectExerciseActivity, Antrenamente::class.java)
                    intent.putExtra("exerciseName", selectedExercise)
                    startActivity(intent)
                }


            }

            override fun afterTextChanged(s: Editable?) {
                // Nu este necesară implementarea acestei metode
            }
        })
    }
}

