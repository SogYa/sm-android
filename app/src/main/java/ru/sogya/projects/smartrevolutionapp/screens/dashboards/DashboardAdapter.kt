package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R

class DashboardAdapter(
    private val onStateClickListener: OnStateClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var states = ArrayList<StateDomain>()

    companion object {
        private const val IS_SENSOR = 0
        private const val IS_SUN = 1
        private const val IS_USER = 2
        private const val IS_SWITCH = 3
        private const val IS_MEDIA_PLAYER = 4
    }

    class SensorWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itTextView: TextView = itemView.findViewById(R.id.textViewId)
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewLable)
        val textViewState: TextView = itemView.findViewById(R.id.textViewState)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }

    class SunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewLable)
        val textViewState: TextView = itemView.findViewById(R.id.textViewState)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewUserLable)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewUserIcon)
    }

    class SwitchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewSwitchLable)
        val textViewId: TextView = itemView.findViewById(R.id.textViewSwitchId)
        val switchState: SwitchCompat = itemView.findViewById(R.id.switchState)

    }

    class MediaPLayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewTittle)
        val textViewId: TextView = itemView.findViewById(R.id.textViewEntityId)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val buttonPowerOn: ImageButton = itemView.findViewById(R.id.imageButtonPowerPOn)
        val buttonPrivios: ImageButton = itemView.findViewById(R.id.imageButtonPrivios)
        val buttonPlay: ImageButton = itemView.findViewById(R.id.imageButtonPlay)
        val buttonNext: ImageButton = itemView.findViewById(R.id.imageButtonNext)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.textFriendlyName)
        val stateTV: TextView = itemView.findViewById(R.id.textState)
        val lastChangeTV: TextView = itemView.findViewById(R.id.textId)
    }


    override fun getItemViewType(position: Int): Int {
        val entityId = states[position].entityId
        if (entityId.startsWith("sensor.")) {
            return IS_SENSOR
        } else if (entityId.startsWith("sun.")) {
            return IS_SUN
        } else if (entityId.startsWith("person."))
            return IS_USER
        else if (entityId.startsWith("switch.")) {
            return IS_SWITCH
        } else if (entityId.startsWith("media_player.")) {
            return IS_MEDIA_PLAYER
        }
        return -1
    }

    override fun getItemCount(): Int {
        return states.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            IS_SENSOR -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_sensor_weather_item, parent, false)
                return SensorWeatherViewHolder(view)
            }
            IS_SUN -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_sun_item, parent, false)
                return SunViewHolder(view)
            }
            IS_USER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_user_item, parent, false)
                return UserViewHolder(view)
            }
            IS_SWITCH -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_switch_item, parent, false)
                return SwitchViewHolder(view)
            }
            IS_MEDIA_PLAYER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_media_player, parent, false)
                return MediaPLayerViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_default_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stateDomain: StateDomain = states[position]
        when (holder) {
            is SensorWeatherViewHolder -> {
                when (stateDomain.attributesDomain?.deviceClass) {
                    "temperature", "humidity" -> {
                        holder.textViewState.text = buildString {
                            append(stateDomain.state)
                            append(stateDomain.attributesDomain?.unitOfMeasurement)
                        }
                        holder.iconView.setImageResource(R.drawable.ic_thermometer)
                    }
                    else -> {
                        holder.textViewState.text = stateDomain.state
                    }
                }
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
                holder.itTextView.text = stateDomain.entityId

            }
            is SunViewHolder -> {
                if (stateDomain.state == "above_horizon") {
                    holder.iconView.setImageResource(R.drawable.ic_sun)
                    holder.textViewState.text = "Above the horizon"
                } else {
                    holder.iconView.setImageResource(R.drawable.ic_moon)
                    holder.textViewState.text = "Below the horizon"
                }
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
            }
            is UserViewHolder -> {
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
                holder.iconView.setImageResource(R.drawable.ic_person)
            }
            is SwitchViewHolder -> {
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
                holder.textViewId.text = stateDomain.entityId
                holder.switchState.text = stateDomain.state
                holder.switchState.isChecked = stateDomain.state == "on"
                holder.switchState.setOnCheckedChangeListener { _, isChecked ->
                    val state = if (isChecked) {
                        "turn_on"
                    } else
                        "turn_off"
                    onStateClickListener?.onSwitchStateChanged(stateDomain.entityId, state)
                }
            }
            is MediaPLayerViewHolder -> {
                if (stateDomain.state == "playing") {
                    holder.buttonPlay.setImageResource(R.drawable.baseline_pause_24)
                } else {
                    holder.buttonPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                }
                holder.textViewId.text = stateDomain.attributesDomain?.friendlyName
                holder.buttonPowerOn.setOnClickListener {
                    if (stateDomain.state == "off") {
                        onStateClickListener?.onClickMediaPlayer(stateDomain.entityId, "turn_on")
                    } else {
                        onStateClickListener?.onClickMediaPlayer(stateDomain.entityId, "turn_off")
                    }
                }
                holder.buttonPrivios.setOnClickListener {
                    onStateClickListener?.onClickMediaPlayer(
                        stateDomain.entityId,
                        "media_previous_track"
                    )
                }
                holder.buttonPlay.setOnClickListener {
                    if (stateDomain.state == "playing") {
                        onStateClickListener?.onClickMediaPlayer(
                            stateDomain.entityId,
                            "media_pause"
                        )
                    } else {
                        onStateClickListener?.onClickMediaPlayer(
                            stateDomain.entityId,
                            "media_play"
                        )
                    }

                }
                holder.buttonNext.setOnClickListener {
                    onStateClickListener?.onClickMediaPlayer(
                        stateDomain.entityId,
                        "media_next_track"
                    )
                }
            }
            is ViewHolder -> {
                holder.idTextView.text = stateDomain.attributesDomain!!.friendlyName
                holder.stateTV.text = stateDomain.state
                holder.lastChangeTV.text = stateDomain.lastChanged
            }
        }
        holder.itemView.setOnClickListener {
            onStateClickListener?.onClick(stateDomain)
        }
        holder.itemView.setOnLongClickListener {
            onStateClickListener?.onLongClick(stateDomain)
            return@setOnLongClickListener true
        }
    }

    fun updateStatesList(statesArrayList: List<StateDomain>) {
        this.states.clear()
        notifyItemChanged(1)
        this.states.addAll(statesArrayList)
        notifyItemRangeChanged(0, states.size)
    }

    interface OnStateClickListener {
        fun onClick(stateDomain: StateDomain)
        fun onLongClick(stateDomain: StateDomain)
        fun onSwitchStateChanged(stateId: String, switchState: String) {}
        fun onClickMediaPlayer(stateId: String, command: String) {}
    }
}
