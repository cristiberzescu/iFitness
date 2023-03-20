package com.example.ifitness.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Food

class FoodListAdapter(private val foodList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.food_design, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFood = foodList[position]

        holder.name.text = currentFood.name
        holder.calories.text = currentFood.calories.toString() + "kcal"
        holder.protein.text = currentFood.protein.toString() + "g"
        holder.carbs.text = currentFood.carbs.toString() + "g"
        holder.fat.text = currentFood.fat.toString() + "g"
        holder.grams.text = currentFood.grams.toString() + "grams"

    }


    override fun getItemCount(): Int {
        return foodList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.firebase_food_name)
        val calories: TextView = itemView.findViewById(R.id.firebase_food_calories)
        val protein: TextView = itemView.findViewById(R.id.firebase_food_protein)
        val carbs: TextView = itemView.findViewById(R.id.firebase_food_carbs)
        val fat: TextView = itemView.findViewById(R.id.firebase_food_fat)
        val grams: TextView = itemView.findViewById(R.id.firebase_food_grams)


    }


}