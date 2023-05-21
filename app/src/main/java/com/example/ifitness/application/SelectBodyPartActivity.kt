package com.example.ifitness.application

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ifitness.R

class SelectBodyPartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_group_measurement)

        val listView = findViewById<ListView>(R.id.list_view)

        val bodyParts = listOf(
            "Neck",
            "Shoulders",
            "Chest",
            "Left Bicep",
            "Right Bicep",
            "Left Forearm",
            "Right Forearm",
            "Waist",
            "Hips",
            "Left Thigh",
            "Right Thigh",
            "Left Calf",
            "Right Calf"
        )

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bodyParts)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = bodyParts[position]

            Toast.makeText(this, "Ai selectat: $selectedItem", Toast.LENGTH_SHORT).show()
        }


    }
}



