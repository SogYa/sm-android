package ru.sogya.projects.smartrevolutionapp.screens.home.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.data.utils.Constants
import com.sogya.domain.models.StateGroupDomain
import ru.sogya.projects.smartrevolutionapp.R

class GroupAdapter(
    private val onGroupClickListener: OnGroupClickListener,
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var groups = ArrayList<StateGroupDomain>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupLabel: TextView = itemView.findViewById(R.id.group_label)
        val groupDesc: TextView = itemView.findViewById(R.id.group_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.state_default_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        groups[0] = StateGroupDomain(
            Constants.DEFAULT_GROUP_ID,
            "All states",
            "A shared dashboard with all the states added to the app"
        )
        holder.groupLabel.text = group.groupTag
        val groupDesc = group.groupDesc
        if (groupDesc != "") {
            holder.groupDesc.text = groupDesc
        } else {
            holder.groupDesc.text = holder.itemView.context.getString(R.string.group_empty_desc)
        }
        holder.itemView.setOnClickListener {
            onGroupClickListener.onClick(group)
        }
        holder.itemView.setOnLongClickListener {
            onGroupClickListener.onLongClick(group)
            return@setOnLongClickListener true
        }

    }

    fun updateGroupsList(groupsList: List<StateGroupDomain>) {
        groups.clear()
        notifyItemChanged(1)
        groups.addAll(groupsList)
        notifyItemRangeChanged(0, groups.size)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    interface OnGroupClickListener {
        fun onClick(stateGroupDomain: StateGroupDomain)
        fun onLongClick(stateGroupDomain: StateGroupDomain)
    }
}