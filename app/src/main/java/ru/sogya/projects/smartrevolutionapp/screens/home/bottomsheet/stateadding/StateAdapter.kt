package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R

class StateAdapter : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    private var states = ArrayList<StateDomain>()
    private val checkedSet = HashSet<StateDomain>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textFriendlyName)
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val stateCountTextView:TextView = itemView.findViewById(R.id.textStateCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_default_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return states.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stateDomain: StateDomain = states[position]
        if (checkedSet.contains(stateDomain)){
            holder.nameTextView.setTextColor(Color.parseColor("FF312EE9"))
        }
        holder.nameTextView.text = stateDomain.attributes!!.friendlyName
        holder.idTextView.text = stateDomain.entityId
        holder.stateCountTextView.text = (position+1).toString()
        holder.itemView.setOnLongClickListener {
            checkedSet.add(stateDomain)
        }
    }

    fun sendCheckedSet(): HashSet<StateDomain> = checkedSet
    fun clearCheckedSet() {
        checkedSet.clear()
    }

    fun updateStatesList(statesArrayList: List<StateDomain>) {
        this.states.clear()
        notifyItemChanged(1)
        this.states.addAll(statesArrayList)
        notifyItemRangeChanged(0, states.size)
    }
}