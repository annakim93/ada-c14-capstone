package com.example.adacapstone.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact

class ContactsRecyclerAdapter : RecyclerView.Adapter<ContactsRecyclerAdapter.MyViewHolder>() {

    private var contactsList = emptyList<Contact>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        return ContactsRecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactsList[position]
        val contactName = holder.itemView.findViewById<TextView>(R.id.contact_name)
        val contactNum = holder.itemView.findViewById<TextView>(R.id.contact_number)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun setData(contacts: List<Contact>) {
        this.contactsList = contacts
        notifyDataSetChanged()
    }
}