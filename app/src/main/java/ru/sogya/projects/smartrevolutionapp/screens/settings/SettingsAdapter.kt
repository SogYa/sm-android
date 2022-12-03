package ru.sogya.projects.smartrevolutionapp.screens.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.models.Settings

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {
    private var settings = ArrayList<Settings>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.settings_label)
        val imageViewSettings: ImageView = itemView.findViewById(R.id.settings_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.settings_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settings: Settings = settings[position]
        holder.labelTextView.text = settings.label
        holder.imageViewSettings.setImageResource(settings.icon)
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}
