package com.example.ifitness.application.tracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.ifitness.R
class SelectExerciseActivity : ComponentActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchListView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val initialData = mutableListOf(
        "impins la piept",
        "fluturari",
        "genoflexiuni",
        "tractiuni",
        "flexii biceps",
        "extensii triceps",
        "indreptari"
    )
    private val filteredData = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_exercise)

        searchEditText = findViewById(R.id.search_edittext)
        searchListView = findViewById(R.id.search_listview)
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

