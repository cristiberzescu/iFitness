package com.example.ifitness.adaptors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.application.Antrenamente
import com.example.ifitness.application.MainActivity
import com.example.ifitness.domain.Series
import com.example.ifitness.domain.Workout

class WorkoutListAdapter(private val workoutsList: ArrayList<Workout>, val context:Context) :
    RecyclerView.Adapter<WorkoutListAdapter.WorkoutsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.workout_design_tracking, parent, false)
        return WorkoutsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        val workout = workoutsList[position]
        holder.nameWorkout.text = workout.name
        holder.dateWorkout.text = workout.date
        holder.itemView.setOnClickListener{
            val intent = Intent(context, Antrenamente::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val bundle = Bundle()
            bundle.putParcelable("workout", workout)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = workoutsList.size

    class WorkoutsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameWorkout: TextView = itemView.findViewById(R.id.workout_name)
        val dateWorkout: TextView = itemView.findViewById(R.id.workout_date)

    }


}