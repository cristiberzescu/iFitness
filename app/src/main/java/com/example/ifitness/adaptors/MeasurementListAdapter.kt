package com.example.ifitness.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Measurement

class MeasurementListAdapter(private val measurementList: ArrayList<Measurement>) :
    RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.measurement_design, parent, false)
        return MeasurementViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        val measurement = measurementList[position]
        holder.valueMeasurement.text = measurement.value
        holder.dateMeasurement.text = measurement.date
    }


    override fun getItemCount(): Int {
        return measurementList.size
    }

    class MeasurementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valueMeasurement: TextView = itemView.findViewById(R.id.measurement_value)
        val dateMeasurement: TextView = itemView.findViewById(R.id.measurement_date)
    }
}
