package fr.isen.lucasgarciarota.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter (val contactsList: ArrayList<Contact>):RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(contact: Contact) {
            val textName = itemView.findViewById<AppCompatTextView>(R.id.contact_name) as AppCompatTextView
            val textPhoneNumber = itemView.findViewById<AppCompatTextView>(R.id.contact_phone_number) as AppCompatTextView

            textName.text = contact.name
            textPhoneNumber.text = contact.phoneNumber
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.bindItem(contactsList[position])
    }

}