package com.example.ifitness.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.dialogs.AddSeriesDialog
import com.example.ifitness.domain.Exercise
import com.example.ifitness.domain.Series

class ExerciseListAdapter(private val exerciseList: ArrayList<Exercise>, var context: Context) :
    RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_design, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.nameExercise.text = exercise.name

        holder.seriesRecyclerView.layoutManager = LinearLayoutManager(context)
        holder.seriesRecyclerView.setHasFixedSize(true)

        holder.seriesRecyclerView.adapter =
            SeriesListAdapter(exercise.series as ArrayList<Series>)

        holder.addSeriesButton.setOnClickListener {
            val dialog = AddSeriesDialog(context, this, position)
            dialog.show()
        }
    }

    fun addSeries(repetitions: Int, weight: Int, position: Int) {
        val exercise = exerciseList[position]
        val series = Series(repetitions, weight)
        exercise.series!!.add(series)
        notifyDataSetChanged()
    }

    override fun getItemCount() = exerciseList.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameExercise: TextView = itemView.findViewById(R.id.exercise_name)
        val seriesRecyclerView: RecyclerView = itemView.findViewById(R.id.series_list)
        val seriesAdapter = SeriesListAdapter(ArrayList<Series>())
        val addSeriesButton: Button = itemView.findViewById(R.id.add_series)

    }


}