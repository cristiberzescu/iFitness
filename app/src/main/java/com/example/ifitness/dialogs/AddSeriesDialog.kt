package com.example.ifitness.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.ifitness.R
import com.example.ifitness.adaptors.ExerciseListAdapter

class AddSeriesDialog(context: Context, var listener: ExerciseListAdapter, var position: Int) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_series_dialog)

        val weight = findViewById<EditText>(R.id.add_weight)
        val repetitions = findViewById<EditText>(R.id.add_repetitions)
        val addButton = findViewById<Button>(R.id.add_series_button)

        addButton.setOnClickListener {
            if (weight.text.toString() != "" && repetitions.text.toString() != "") {
                listener.addSeries(
                    repetitions.text.toString().toInt(),
                    weight.text.toString().toInt(),
                    position
                )
                dismiss()
            }
        }
    }
}