package com.example.ifitness.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.FoodCharacteristics

class FoodListAdapter(private var foodList: ArrayList<Food>//, private val listener: OnAddButtonClickListener
) :
    RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

//    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val foodName: TextView = itemView.findViewById(R.id.firebase_food_name)
//        val addBtn: Button = itemView.findViewById(R.id.firebase_add_food)
//
//        fun bind(food: Food) {
//            foodName.text = food.name
//            addBtn.setOnClickListener {
//                listener.onAddButtonClick(food)
//            }
//        }
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.food_design, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFood = foodList[position]

        holder.name.text = currentFood.name
        holder.calories.text = currentFood.calories.toString() + "cal"
        holder.protein.text = currentFood.protein.toString() + "g"
        holder.carbs.text = currentFood.carbs.toString() + "g"
        holder.fat.text = currentFood.fat.toString() + "g"
        holder.grams.text = currentFood.grams.toString() + "g"
        holder.btn_add.setOnClickListener {
            FoodCharacteristics.setTitle(currentFood.name)
        }
//        val food = foodList[position]
//        holder.bind(food)

    }


    override fun getItemCount(): Int {
        return foodList.size
    }

    interface OnAddButtonClickListener {
        fun onAddButtonClick(food: Food)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.firebase_food_name)
        val calories: TextView = itemView.findViewById(R.id.firebase_food_calories)
        val protein: TextView = itemView.findViewById(R.id.firebase_food_protein)
        val carbs: TextView = itemView.findViewById(R.id.firebase_food_carbs)
        val fat: TextView = itemView.findViewById(R.id.firebase_food_fat)
        val grams: TextView = itemView.findViewById(R.id.firebase_food_grams)
        val btn_add: Button = itemView.findViewById(R.id.firebase_add_food)
    }


}