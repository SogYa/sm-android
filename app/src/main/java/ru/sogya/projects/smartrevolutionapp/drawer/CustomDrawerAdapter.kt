package ru.sogya.projects.smartrevolutionapp.drawer


import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import ru.sogya.projects.smartrevolutionapp.R


class CustomDrawerAdapter(
    context: Context,
    private val resourceId: Int,
    private val itemList: List<DrawerItem>
) :
    ArrayAdapter<DrawerItem>(context, resourceId, itemList) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//        val drawerHolder: DrawerItemHolder
//        var view = convertView
//        if (view == null) {
//            val inflater = (context as Activity).layoutInflater
//            drawerHolder = DrawerItemHolder()
//            view = inflater.inflate(resourceId, parent, false)
//            drawerHolder.itemTitle = view.findViewById<View>(R.id.textLable) as TextView
//            drawerHolder.itemDesc = view.findViewById<View>(R.id.textDesc) as TextView
//            drawerHolder.icon = view.findViewById<View>(R.id.itemIcon) as ImageView
//            view.tag = drawerHolder
//        } else {
//            drawerHolder = view.tag as DrawerItemHolder
//        }
//        val item = this.itemList[position]
//        drawerHolder.icon?.setImageResource(item.imageId)
//        drawerHolder.itemTitle?.text = item.title
//        drawerHolder.itemDesc?.text = item.description
//        return view
//    }
//
//    private class DrawerItemHolder {
//        var itemTitle: TextView? = null
//        var itemDesc: TextView? = null
//        var icon: ImageView? = null
//    }
}