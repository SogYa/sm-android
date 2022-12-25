package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R

class StateAdapter(
    private val onStateClickListenner: OnStateClickListener
) : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    private var states = ArrayList<StateDomain>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val stateTV: TextView = itemView.findViewById(R.id.textState)
        val lastChangeTV: TextView = itemView.findViewById(R.id.textLastChanged)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.state_default_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stateDomain: StateDomain = states[position]
        holder.idTextView.text = stateDomain.entityId
        holder.stateTV.text = stateDomain.state
        holder.lastChangeTV.text = stateDomain.lastChanged
        holder.itemView.setOnClickListener {
            onStateClickListenner.onClick(stateDomain)
        }
    }

    fun updateStatesList(statesArrayList: List<StateDomain>) {
        this.states.clear()
        notifyItemChanged(1)
        this.states.addAll(statesArrayList)
        notifyItemRangeChanged(0, states.size)
    }

    override fun getItemCount(): Int {
        return states.size
    }

    interface OnStateClickListener {
        fun onClick(stateDomain: StateDomain)
    }
}
