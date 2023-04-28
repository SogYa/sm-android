package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import android.graphics.Color
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

    companion object {
        const val YELLOW = "#CC5500"
        const val GREEN = "#00FF00"
        const val RED = "#FF0000"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ticketId: TextView = itemView.findViewById(R.id.textTicketId)
        val ticketDate: TextView = itemView.findViewById(R.id.textTicketDate)
        val ticketStatus: TextView = itemView.findViewById(R.id.textTicketStatus)
        val ticketCounter:TextView = itemView.findViewById(R.id.textViewCounter)

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
        val currentPsition = position+1
        holder.ticketId.text = ticket.ticketId
        holder.ticketDate.text = ticket.ticketDate
        holder.ticketStatus.text = ticket.ticketStatus
        holder.ticketCounter.text = currentPsition.toString()
        when (ticket.ticketStatus) {
            "Created" -> holder.ticketStatus.setTextColor(Color.parseColor(YELLOW))
            "Done" -> holder.ticketStatus.setTextColor(Color.parseColor(GREEN))
            "Canceled" -> holder.ticketStatus.setTextColor(Color.parseColor(RED))
        }
        holder.itemView.setOnClickListener {
            onTicketClickListener.onClick(ticket)
        }
        holder.itemView.setOnLongClickListener {
            onTicketClickListener.onLongClick(ticket)
            return@setOnLongClickListener true
        }
    }

    fun updateList(ticketList: Map<String, TicketDomain>) {
        val list = ticketList.values.toList()
        ticketArrayList.clear()
        notifyItemChanged(1)
        ticketArrayList.addAll(list)
        notifyItemRangeChanged(0, ticketList.size)
    }
}
