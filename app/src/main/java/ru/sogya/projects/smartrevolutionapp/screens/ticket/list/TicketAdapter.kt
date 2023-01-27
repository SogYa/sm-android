package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.TicketDomain
import ru.sogya.projects.smartrevolutionapp.R

class TicketAdapter(
    private val onTicketClickListener: OnTicketClickListener
) : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    private val ticketArrayList = arrayListOf<TicketDomain>()

    interface OnTicketClickListener {
        fun onClick(ticketDomain: TicketDomain)
        fun onLongClick(ticketDomain: TicketDomain)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ticketId: TextView = itemView.findViewById(R.id.textTicketId)
        val ticketDate: TextView = itemView.findViewById(R.id.textTicketDate)
        val ticketStatus: TextView = itemView.findViewById(R.id.textTicketStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ticketArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = ticketArrayList[position]
        holder.ticketId.text = ticket.ticketId
        holder.ticketDate.text = ticket.ticketDate
        holder.ticketStatus.text = ticket.ticketStatus
        holder.itemView.setOnClickListener {
            onTicketClickListener.onClick(ticket)
        }
        holder.itemView.setOnLongClickListener {
            onTicketClickListener.onLongClick(ticket)
            return@setOnLongClickListener true
        }
    }

    fun updateList(ticketList: List<TicketDomain>) {
        ticketArrayList.clear()
        notifyItemChanged(1)
        ticketArrayList.addAll(ticketList)
        notifyItemRangeChanged(0, ticketList.size)
    }
}
