package fr.isen.duee.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact.view.*

class ContactAdapter(val contacts: List<Contact>, val clickListener: (Contact) -> Unit) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position], clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
            itemView.contactName.text = contact.name
            itemView.contactPhoneNumberValue.text = contact.number
            itemView.setOnClickListener { clickListener(contact)}
        }
    }
}