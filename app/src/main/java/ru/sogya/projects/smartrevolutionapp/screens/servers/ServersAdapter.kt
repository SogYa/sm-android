package ru.sogya.projects.smartrevolutionapp.screens.servers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.ServerStateDomain
import ru.sogya.projects.smartrevolutionapp.R

class ServersAdapter(
    private val onServerClickListener: OnServerClickListener
) : RecyclerView.Adapter<ServersAdapter.ViewHolder>() {
    private var serverList = ArrayList<ServerStateDomain>()

    interface OnServerClickListener {
        fun onClick(server: ServerStateDomain)
        fun onLongClick(server: ServerStateDomain)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textName)
        val uriTextView: TextView = itemView.findViewById(R.id.textUri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.server_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val server = serverList[position]
        holder.nameTextView.text = server.serverName
        holder.uriTextView.text = server.serverUri
        holder.itemView.setOnClickListener {
            onServerClickListener.onClick(server)
        }
        holder.itemView.setOnLongClickListener{
            onServerClickListener.onLongClick(server)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return serverList.size
    }

    fun updateList(list: List<ServerStateDomain>) {
        serverList.clear()
        notifyItemChanged(1)
        serverList.addAll(list)
        notifyItemRangeChanged(0, serverList.size)
    }
}