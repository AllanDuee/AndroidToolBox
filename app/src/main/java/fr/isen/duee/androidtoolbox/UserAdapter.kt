package fr.isen.duee.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact.view.*

class UsersAdapter(val users: List<User>, val clickListener: (User) -> Unit) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*holder.firstName.text = users[position].firstName
        holder.lastName.text = users[position].lastName
        holder.number.text = users[position].number
        holder.email.text = users[position].email
        holder.birthday.text = users[position].dateOfBirth*/
        holder.bind(users[position], clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User, clickListener: (User) -> Unit) {
            itemView.contact_firstName.text = user.firstName
            itemView.contact_lastName.text = user.lastName
            itemView.contact_number_value.text = user.number
            itemView.contact_birthday_value.text = user.dateOfBirth
            itemView.contact_email_value.text = user.email
            itemView.setOnClickListener { clickListener(user)}
        }
       /* val firstName: TextView = view.contact_firstName
        val lastName: TextView = view.contact_lastName
        val number: TextView = view.contact_number_value
        val email: TextView = view.contact_email_value
        val birthday: TextView = view.contact_birthday_value*/
    }
}