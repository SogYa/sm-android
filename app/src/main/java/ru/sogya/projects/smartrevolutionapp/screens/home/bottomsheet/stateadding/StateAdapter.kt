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

class StateAdapter : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    private var states = ArrayList<StateDomain>()
    private val checkedSet = HashSet<StateDomain>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textFriendlyName)
        val stateTextView: TextView = itemView.findViewById(R.id.textState)
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val card: CardView = itemView.findViewById(R.id.cardItem)
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
        holder.nameTextView.text = stateDomain.attributesDomain!!.friendlyName
        holder.stateTextView.text = stateDomain.state
        holder.idTextView.text = stateDomain.entityId
        if (!checkedSet.contains(stateDomain)) {
            holder.card.setCardBackgroundColor(Color.WHITE)
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor(LIGHT_PINK))
        }
        holder.itemView.setOnLongClickListener {
            when (holder.card.cardBackgroundColor.defaultColor) {
                Color.parseColor(LIGHT_PINK) -> {
                    holder.card.setCardBackgroundColor(Color.WHITE)
                    checkedSet.remove(states[position])
                }
                Color.WHITE -> {
                    holder.card.setCardBackgroundColor(Color.parseColor(LIGHT_PINK))
                    checkedSet.add(states[position])
                }
            }
            return@setOnLongClickListener true
        }
    }

    fun sendCheckedSet(): HashSet<StateDomain> = checkedSet
    fun clearCheckedSet() {
        checkedSet.clear()
    }

    companion object {
        private const val LIGHT_PINK = "#FFE0EB"
    }

    fun updateStatesList(statesArrayList: List<StateDomain>) {
        this.states.clear()
        notifyItemChanged(1)
        this.states.addAll(statesArrayList)
        notifyItemRangeChanged(0, states.size)
    }
}