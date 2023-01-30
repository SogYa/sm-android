package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R

class StateAdapter(
    private val onStateClickListener: OnStateClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var states = ArrayList<StateDomain>()
    private val checkedSet = HashSet<StateDomain>()

    companion object {
        private const val LIGHT_PINK = "#FFE0EB"
        private const val IS_SENSOR = 0
    }

    class SensorWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itTextView: TextView = itemView.findViewById(R.id.textViewId)
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewLable)
        val textViewState: TextView = itemView.findViewById(R.id.textViewState)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val stateTV: TextView = itemView.findViewById(R.id.textState)
        val lastChangeTV: TextView = itemView.findViewById(R.id.textLastChanged)
    }

    override fun getItemViewType(position: Int): Int {
        if (states[position].entityId.startsWith("sensor.")) {
            return IS_SENSOR
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == IS_SENSOR) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.state_sensor_weather_item, parent, false)
            return SensorWeatherViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.state_default_item, parent, false)
            return ViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stateDomain: StateDomain = states[position]
        if (holder is SensorWeatherViewHolder) {
            if (stateDomain.attributesDomain?.deviceClass == "temperature") {
                holder.iconView.setImageResource(R.drawable.temperature_thermometer_cold_warm_hot_icon_194210)
                holder.textViewState.text = "${stateDomain.state}°C"
            } else if (stateDomain.attributesDomain?.deviceClass == "humidity") {
                holder.iconView.setImageResource(R.drawable.humidity_icon_216457)
                holder.textViewState.text = "${stateDomain.state}%"
            }
            holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
            holder.itTextView.text = stateDomain.entityId

        } else if (holder is ViewHolder) {
            holder.idTextView.text = stateDomain.attributesDomain!!.friendlyName
            holder.stateTV.text = stateDomain.state
            holder.lastChangeTV.text = stateDomain.lastChanged
        }
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
