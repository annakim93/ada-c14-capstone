package com.example.adacapstone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.fragments.ContactsFragment
import com.example.adacapstone.fragments.ContactsFragmentDirections
import com.example.adacapstone.fragments.SetContactsForImgMsgFragment

class ContactSelectionRecyclerAdapter(val fragment: SetContactsForImgMsgFragment) :
        RecyclerView.Adapter<ContactSelectionRecyclerAdapter.MyViewHolder>() {

    var contactsList = emptyList<Contact>()
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

        // Make checkboxes visible
        checkBox = holder.itemView.findViewById(R.id.checkbox)
        checkBox.visibility = View.VISIBLE

//        // Selected items are checked off
//        checkBox.isChecked = selectedItems.contains(currentItem)

        // Normal click handler
        layoutItem.setOnClickListener {
            if (selectedItems.size == 0) {
                fragment.startSelection(currentItem)
            } else {
                fragment.manageSelection(currentItem)
            }
            selectItem(holder, currentItem)
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