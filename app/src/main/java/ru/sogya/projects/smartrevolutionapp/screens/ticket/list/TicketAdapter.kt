package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sogya.projects.smartrevolutionapp.R

class TicketAdapter : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ticketId = itemView.findViewById<TextView>(R.id.textTicketId)
        val ticketDate = itemView.findViewById<TextView>(R.id.textTicketDate)
        val ticketStatus = itemView.findViewById<TextView>(R.id.textTicketStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
