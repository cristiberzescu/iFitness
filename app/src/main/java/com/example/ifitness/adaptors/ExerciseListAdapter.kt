package com.example.ifitness.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Exercise
import com.example.ifitness.domain.Series
import com.example.ifitness.domain.VideoExercise

class ExerciseListAdapter(private val exerciseList: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_design, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.nameExercise.text = exercise.name
        holder.setSeriesList(exercise.series as ArrayList<Series>)
    }



    override fun getItemCount() = exerciseList.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameExercise: TextView = itemView.findViewById(R.id.exercise_name)
        val seriesRecyclerView: RecyclerView = itemView.findViewById(R.id.foodList)
        val seriesAdapter = SeriesListAdapter(ArrayList<Series>())

        fun setSeriesList(seriesList: ArrayList<Series>) {
            seriesAdapter.setSeriesList(seriesList)
            seriesRecyclerView.adapter?.notifyDataSetChanged()
        }
    }


}