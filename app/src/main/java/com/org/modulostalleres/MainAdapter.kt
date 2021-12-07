package com.org.modulostalleres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {
    private val activityNames = listOf<String>("Basket","Futbol","Natacion","Bodybuilding")

    //numOfItems
    override fun getItemCount(): Int {
        return homeFeed.talleres.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //how do we even create a view?
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.activiry_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val videoName = activityNames[position]
        val taller = homeFeed.talleres[position]
        holder.view.findViewById<TextView>(R.id.textViewActivityName).text = taller.nombre_actividad
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
}