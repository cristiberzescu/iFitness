package com.example.ifitness.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.domain.Food

class MyAdapter(private val serviceList: ArrayList<Food>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.food_design, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentService = serviceList[position]

        holder.title.text = currentService.serviceTitle
        holder.description.text = currentService.serviceDescription
        holder.phoneNumber.text = currentService.servicePhone
        holder.price.text = currentService.servicePrice + " Lei"
        holder.location.text = currentService.serviceLocation

    }


    override fun getItemCount(): Int {
        return serviceList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.firebase_job_title)
        val description: TextView = itemView.findViewById(R.id.firebase_job_gramsss)
        val phoneNumber: TextView = itemView.findViewById(R.id.firebase_job_phone)
        val price: TextView = itemView.findViewById(R.id.firebase_job_price)
        val location: TextView = itemView.findViewById(R.id.firebase_job_locationss)


    }


}