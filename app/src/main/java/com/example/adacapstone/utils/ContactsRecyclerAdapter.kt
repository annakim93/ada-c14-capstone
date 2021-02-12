package com.example.adacapstone.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.fragments.ContactsFragment

class ContactsRecyclerAdapter(val fragment: ContactsFragment) : RecyclerView.Adapter<ContactsRecyclerAdapter.MyViewHolder>() {

    var contactsList = emptyList<Contact>()
    var multiSelect = false
    val selectedItems = arrayListOf<Contact>()
    lateinit var checkBox: CheckBox


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactsList[position]

        // View items
        val layoutItem = holder.itemView.findViewById<RelativeLayout>(R.id.contact_list_item_layout)

        val contactName = holder.itemView.findViewById<TextView>(R.id.contact_name)
        contactName.text = currentItem.name
        val contactNum = holder.itemView.findViewById<TextView>(R.id.contact_number)
        contactNum.text = currentItem.phoneNumber

        checkBox = holder.itemView.findViewById(R.id.checkbox)

        if (multiSelect) {
            checkBox.visibility = View.VISIBLE
        } else {
            checkBox.visibility = View.GONE
        }

        // Selected items are checked off
        checkBox.isChecked = selectedItems.contains(currentItem)

        // Long-click handler - selection
        layoutItem.setOnLongClickListener {
            if (!multiSelect) {
                multiSelect = true
                selectItem(holder, currentItem)
                fragment.startSelection(currentItem)
                notifyDataSetChanged()
            }
            true
        }

        // Normal click handler
        layoutItem.setOnClickListener {
            if (multiSelect) {
                selectItem(holder, currentItem)
                fragment.manageSelection(currentItem)
                if (selectedItems.size == 0) {
                    multiSelect = false
                    notifyDataSetChanged()
                }
            } else {
//                val action = ManageGridFragmentDirections.actionManageGridToUpdateFragment(currentItem)
//                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun setData(contacts: List<Contact>) {
        this.contactsList = contacts
        notifyDataSetChanged()
    }

    private fun selectItem(holder: MyViewHolder, contact: Contact) {
        if (selectedItems.contains(contact)) {
            selectedItems.remove(contact)
            holder.itemView.findViewById<CheckBox>(R.id.checkbox).isChecked = false
        } else {
            selectedItems.add(contact)
            holder.itemView.findViewById<CheckBox>(R.id.checkbox).isChecked = true
        }
    }
}