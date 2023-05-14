package com.example.ifitness.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Exercise
import com.example.ifitness.domain.Series

class SeriesListAdapter(private var seriesList: ArrayList<Series>) :
    RecyclerView.Adapter<SeriesListAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.series_design, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val series = seriesList[position]
        holder.repetitions.text = series.repetitions.toString()
        holder.weight.text = series.weight.toString()
        holder.seriesName.text = "Series ${position + 1}"
    }

    override fun getItemCount() = seriesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setSeriesList(newList: ArrayList<Series>) {
        seriesList = newList
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repetitions: TextView = itemView.findViewById(R.id.firebase_exercise_repetitions)
        val weight: TextView = itemView.findViewById(R.id.firebase_exercise_weight)
        val seriesName: TextView = itemView.findViewById(R.id.series_name)
    }
}