package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.StateAddingFragment

class ViewPagerAdapter(fragment: Fragment, private val pageList: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemView.requestLayout()
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return GroupAddingFragment()
            1 -> return StateAddingFragment()
        }
        return GroupAddingFragment()
    }
}