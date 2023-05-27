package com.example.ifitness.application.main

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.ifitness.R

class SelectBodyPartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_group_measurement)

        val listView = findViewById<ListView>(R.id.list_view)
        val backButton = findViewById<Button>(R.id.go_back_button)
        val heightButton = findViewById<Button>(R.id.height)
        val weightButton = findViewById<Button>(R.id.weight)

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

        val bundle = Bundle()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bodyParts)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = bodyParts[position]
            bundle.putString("body_part", selectedItem)

            val intent = Intent(this, MeasurementActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        weightButton.setOnClickListener {
            bundle.putString("body_part", "Weight")

            val intent = Intent(this, MeasurementActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        heightButton.setOnClickListener {
            bundle.putString("body_part", "Height")

            val intent = Intent(this, MeasurementActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}



