package com.example.ifitness.application.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adapters.MeasurementListAdapter
import com.example.ifitness.domain.Measurement
import com.example.ifitness.domain.UserCharacteristics
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MeasurementActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var measurementRecyclerView: RecyclerView
    private lateinit var measurementArrayList: ArrayList<Measurement>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.measurement_activity)
        val backButton = findViewById<Button>(R.id.back_button)
        val addMeasurementButton = findViewById<Button>(R.id.add_measurement)
        val measurementInputEditText = findViewById<EditText>(R.id.edit_measurement)
        val bodyPart = findViewById<TextView>(R.id.body_part)
        val emptyListMessage = findViewById(R.id.tv_empty_list) as TextView
        var bodyPartName: String = "nothing"
        val bundle = intent.extras
        if (bundle != null) {
            bodyPartName = bundle.getString("body_part").toString()
            bodyPart.text = bodyPartName
            database = FirebaseDatabase.getInstance().getReference("users")
                .child(UserCharacteristics.getUsername().toString()).child("measurement")
                .child(bodyPartName)
        }
        measurementRecyclerView = findViewById(R.id.measurement_list)
        measurementRecyclerView.layoutManager = LinearLayoutManager(this)
        measurementRecyclerView.setHasFixedSize(true)
        measurementArrayList = ArrayList<Measurement>()
        backButton.setOnClickListener {
            val intent = Intent(this, SelectBodyPartActivity::class.java)
            startActivity(intent)
        }
        addMeasurementButton.setOnClickListener {
            val measurementValue = measurementInputEditText.text.toString().trim()
            if (measurementValue.isNotEmpty()) {
                val measurementId = database.push().key
                val measurement = Measurement(measurementValue, getCurrentDate())
                measurementId?.let {
                    database.child(it).setValue(measurement)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Measurement added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                measurementInputEditText.text.clear()
                                if (bodyPartName == "Weight") {
                                    UserCharacteristics.setWeight(measurementValue)
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to add measurement",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Please enter a measurement value", Toast.LENGTH_SHORT).show()
            }
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                measurementArrayList.clear()
                if (snapshot.exists()) {
                    for (measurementSnapshot in snapshot.children) {
                        val measurement = measurementSnapshot.getValue(Measurement::class.java)
                        measurementArrayList.add(measurement!!)
                    }
                    measurementRecyclerView.adapter = MeasurementListAdapter(measurementArrayList)
                    emptyListMessage.visibility = View.INVISIBLE
                } else {
                    emptyListMessage.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("MeasurementActivity", "Failed to read value.", error.toException())
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val romaniaTimeZone: ZoneId = ZoneId.of("Europe/Bucharest")
        val today: LocalDate = LocalDate.now(romaniaTimeZone)
        val todayDate: String = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return todayDate
    }
}
