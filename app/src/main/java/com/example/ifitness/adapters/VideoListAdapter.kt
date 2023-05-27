package com.example.ifitness.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.VideoExercise

class VideoListAdapter(private val exerciseList: ArrayList<VideoExercise>) :
    RecyclerView.Adapter<VideoListAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.video_exercise_design, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.nameExercise.text = exercise.name
        holder.videoExercise.setVideoPath(exercise.url)
        holder.videoExercise.start()
        holder.videoExercise.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }
    }

    override fun getItemCount() = exerciseList.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameExercise: TextView = itemView.findViewById(R.id.exercise_name)
        val videoExercise: VideoView = itemView.findViewById(R.id.video_exercise)
    }
}


