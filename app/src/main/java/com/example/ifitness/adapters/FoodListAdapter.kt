package com.example.ifitness.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.application.calories.AddFoodActivity
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics

class FoodListAdapter(
    private var foodList: ArrayList<Food>, var context: Context
) :
    RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.food_design, parent, false)
        return MyViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFood = foodList[position]

        holder.name.text = currentFood.name
        holder.calories.text = currentFood.calories.toString() + "cal"
        holder.protein.text = "Protein: " + String.format("%.1f", currentFood.protein) + "g"
        holder.carbs.text = "Carbs: " + String.format("%.1f", currentFood.carbs) + "g"
        holder.fats.text = "Fat: " + String.format("%.1f", currentFood.fats) + "g"
        holder.grams.text = currentFood.grams.toString() + "g"
        holder.itemView.setOnClickListener {
            FoodCharacteristics.setName(currentFood.name)
            FoodCharacteristics.setProtein(currentFood.protein.toString())
            FoodCharacteristics.setCarbs(currentFood.carbs.toString())
            FoodCharacteristics.setFats(currentFood.fats.toString())
            FoodCharacteristics.setCalories(currentFood.calories.toString())
            val intent = Intent(context, AddFoodActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.firebase_food_name)
        val calories: TextView = itemView.findViewById(R.id.firebase_food_calories)
        val protein: TextView = itemView.findViewById(R.id.firebase_food_protein)
        val carbs: TextView = itemView.findViewById(R.id.firebase_food_carbs)
        val fats: TextView = itemView.findViewById(R.id.firebase_food_fats)
        val grams: TextView = itemView.findViewById(R.id.firebase_food_grams)
    }


}