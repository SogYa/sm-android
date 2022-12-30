package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R

class StateAdapter(
    private val onStateClickListener: OnStateClickListener?
) : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    private var states = ArrayList<StateDomain>()
    private val checkedSet = HashSet<StateDomain>()

    companion object {
        private const val LIGHT_PINK = "#FFE0EB"
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val stateTV: TextView = itemView.findViewById(R.id.textState)
        val lastChangeTV: TextView = itemView.findViewById(R.id.textLastChanged)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.state_default_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stateDomain: StateDomain = states[position]
        holder.idTextView.text = stateDomain.entityId
        holder.stateTV.text = stateDomain.state
        holder.lastChangeTV.text = stateDomain.lastChanged
        holder.itemView.setOnLongClickListener {
            val card = holder.itemView.findViewById<CardView>(R.id.cardItem)
            when (card.cardBackgroundColor.defaultColor) {
                Color.parseColor(LIGHT_PINK) -> {
                    card.setCardBackgroundColor(Color.WHITE)
                    checkedSet.remove(states[position])
                }
                Color.WHITE -> {
                    card.setCardBackgroundColor(Color.parseColor(LIGHT_PINK))
                    checkedSet.add(states[position])
                }
            }
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener {
            onStateClickListener?.onClick(states[position])
        }
    }

    fun updateStatesList(statesArrayList: List<StateDomain>) {
        this.states.clear()
        notifyItemChanged(1)
        this.states.addAll(statesArrayList)
        notifyItemRangeChanged(0, states.size)
    }

    fun sendCheckedSet(): HashSet<StateDomain> = checkedSet
    override fun getItemCount(): Int {
        return states.size
    }

    interface OnStateClickListener {
        fun onClick(stateDomain: StateDomain)
    }
}
