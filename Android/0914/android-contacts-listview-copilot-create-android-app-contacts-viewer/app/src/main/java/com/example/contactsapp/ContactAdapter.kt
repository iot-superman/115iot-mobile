package com.example.contactsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class ContactAdapter(
    context: Context,
    contacts: List<Contact>
) : BaseAdapter(), Filterable {

    private val inflater = LayoutInflater.from(context)
    private val allContacts: List<Contact> = contacts.toList()
    private var filteredContacts: List<Contact> = allContacts.toList()

    override fun getCount(): Int = filteredContacts.size

    override fun getItem(position: Int): Contact = filteredContacts[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder(
                nameText = view.findViewById(R.id.tvName),
                phoneText = view.findViewById(R.id.tvPhone),
                emailText = view.findViewById(R.id.tvEmail)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val contact = getItem(position)
        holder.nameText.text = contact.name
        holder.phoneText.text = contact.phone.ifBlank { parent.context.getString(R.string.no_phone) }
        holder.emailText.text = contact.email.ifBlank { parent.context.getString(R.string.no_email) }

        return view
    }

    override fun getFilter(): Filter = contactFilter

    private val contactFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val query = constraint?.toString()?.trim().orEmpty()
            val results = if (query.isEmpty()) {
                allContacts
            } else {
                allContacts.filter { it.name.contains(query, ignoreCase = true) }
            }

            return FilterResults().apply {
                values = results
                count = results.size
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredContacts = (results?.values as? List<Contact>)?.toList() ?: emptyList()
            notifyDataSetChanged()
        }
    }

    private data class ViewHolder(
        val nameText: TextView,
        val phoneText: TextView,
        val emailText: TextView
    )
}
