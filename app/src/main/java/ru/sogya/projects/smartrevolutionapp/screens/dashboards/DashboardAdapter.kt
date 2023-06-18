package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
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
        private const val IS_CAMERA = 5
        private const val IS_COVER = 6
        private const val IS_BINARY_SENSOR = 7

        private const val IS_OPEN = "open"
        private const val IS_CLOSED = "closed"
        private const val IS_UNAVAILABLE = "unavailable"
        private const val UNAVAILABLE_COLOR = "#AAAAAA"
    }

    class SensorWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textFriendlyName)
        val textViewState: TextView = itemView.findViewById(R.id.textId)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }

    class SunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textFriendlyName)
        val textViewState: TextView = itemView.findViewById(R.id.textSunState)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewUserLable)
        val iconView: ImageView = itemView.findViewById(R.id.imageViewUserIcon)
    }

    class SwitchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texViewLabel: TextView = itemView.findViewById(R.id.textViewSwitchLable)
        val textViewState: TextView = itemView.findViewById(R.id.textViewSwitchState)
        val switchState: SwitchCompat = itemView.findViewById(R.id.switchState)

    }

    class MediaPLayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewMediaPlayerName)
        val textViewId: TextView = itemView.findViewById(R.id.textViewEntityId)
        val buttonPowerOn: ImageButton = itemView.findViewById(R.id.imageButtonPowerPOn)
    }

    class CameraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textCameraName)
        val imageCamera: ImageView = itemView.findViewById(R.id.imageViewCamera)
    }

    class CoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textCoverName)
        val coverSlider: Slider = itemView.findViewById(R.id.coverSlider)
        val buttonUp: AppCompatButton = itemView.findViewById(R.id.buttonCoverUp)
        val buttonDown: AppCompatButton = itemView.findViewById(R.id.buttonCoverDown)
    }

    class BinarySensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textBinaryName)
        val stateTV: TextView = itemView.findViewById(R.id.textBinaryState)
        val idTextView: TextView = itemView.findViewById(R.id.textBinaryId)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textFriendlyName)
        val idTextView: TextView = itemView.findViewById(R.id.textId)
    }


    override fun getItemViewType(position: Int): Int {
        val entityId = states[position].entityId
        return if (entityId.startsWith("sensor.")) {
            IS_SENSOR
        } else if (entityId.startsWith("sun.")) {
            IS_SUN
        } else if (entityId.startsWith("person."))
            IS_USER
        else if (entityId.startsWith("switch.")) {
            IS_SWITCH
        } else if (entityId.startsWith("media_player.")) {
            IS_MEDIA_PLAYER
        } else if (entityId.startsWith("camera.")) {
            IS_CAMERA
        } else if (entityId.startsWith("cover.")) {
            IS_COVER
        } else if (entityId.startsWith("binary_sensor.")) {
            IS_BINARY_SENSOR
        } else
            -1
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

            IS_CAMERA -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.states_camera, parent, false)
                return CameraViewHolder(view)
            }

            IS_COVER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_cover, parent, false)
                return CoverViewHolder(view)
            }

            IS_BINARY_SENSOR -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.state_binary_sensor, parent, false)
                return BinarySensorViewHolder(view)
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
                holder.textViewState.text = buildString {
                    append(stateDomain.state)
                    append(stateDomain.attributesDomain?.unitOfMeasurement)
                }
                when (stateDomain.attributesDomain?.deviceClass) {
                    "temperature" -> {
                        holder.iconView.setImageResource(R.drawable.ic_thermometer)
                    }

                    "humidity" -> {
                        holder.iconView.setImageResource(R.drawable.ic_humidity)
                    }
                }
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
            }

            is SunViewHolder -> {
                if (stateDomain.state == "above_horizon") {
                    holder.iconView.setImageResource(R.drawable.ic_sun)
                    holder.textViewState.text = "Над горизонтом"
                } else {
                    holder.iconView.setImageResource(R.drawable.ic_moon)
                    holder.textViewState.text = "За горизонтом"
                }
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
            }

            is UserViewHolder -> {
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
                holder.iconView.setImageResource(R.drawable.ic_person)
            }

            is SwitchViewHolder -> {
                holder.texViewLabel.text = stateDomain.attributesDomain?.friendlyName
                if (stateDomain.state == "on") {
                    holder.switchState.isChecked = true
                    holder.textViewState.text = "Включено"
                } else {
                    holder.switchState.isChecked = false
                    holder.textViewState.text = "Выключено"
                }
                holder.switchState.setOnCheckedChangeListener { _, isChecked ->
                    val state = if (isChecked) {
                        "turn_on"
                    } else
                        "turn_off"
                    onStateClickListener?.onSwitchStateChanged(stateDomain.entityId, state)
                }
            }

            is MediaPLayerViewHolder -> {
                holder.textViewId.text = stateDomain.attributesDomain?.friendlyName
                holder.buttonPowerOn.setOnClickListener {
                    if (stateDomain.state == "off") {
                        onStateClickListener?.onClickWithCommand(stateDomain.entityId, "turn_on")
                    } else {
                        onStateClickListener?.onClickWithCommand(stateDomain.entityId, "turn_off")
                    }
                }
                holder.textView.text = stateDomain.state

            }

            is CameraViewHolder -> {
                holder.textViewName.text = stateDomain.attributesDomain?.friendlyName

            }

            is CoverViewHolder -> {
                holder.apply {
                    nameTextView.text = stateDomain.attributesDomain?.friendlyName
                    coverSlider.value = stateDomain.attributesDomain?.currentPosition?.toFloat()!!
                    coverSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                        override fun onStartTrackingTouch(slider: Slider) {
                            // Responds to when slider's touch event is being started
                        }

                        override fun onStopTrackingTouch(slider: Slider) {
                            onStateClickListener?.onSliderChangeValue(
                                stateDomain.entityId,
                                slider.value.toInt()
                            )
                        }
                    })
                    when (stateDomain.state) {
                        IS_OPEN -> {
                            buttonDown.isEnabled = true
                            buttonUp.isEnabled = false
                        }

                        IS_CLOSED -> {
                            buttonDown.isEnabled = false
                            buttonUp.isEnabled = true
                        }

                        IS_UNAVAILABLE -> {
                            buttonDown.isEnabled = false
                            buttonUp.isEnabled = false
                        }
                    }
                    buttonUp.setOnClickListener {
                        onStateClickListener?.onClickWithCommand(
                            stateDomain.entityId,
                            "open_cover"
                        )
                    }
                    buttonDown.setOnClickListener {
                        onStateClickListener?.onClickWithCommand(
                            stateDomain.entityId,
                            "close_cover"
                        )
                    }
                }
            }

            is BinarySensorViewHolder -> {
                holder.nameTextView.text = stateDomain.attributesDomain!!.friendlyName
                holder.stateTV.text = stateDomain.state
                holder.idTextView.text = stateDomain.lastChanged
            }

            is ViewHolder -> {
                holder.nameTextView.text = stateDomain.attributesDomain!!.friendlyName
                holder.idTextView.text = stateDomain.lastChanged
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
        fun onClickWithCommand(stateId: String, command: String) {}
        fun onSliderChangeValue(stateId: String, value: Int) {}
    }
}
