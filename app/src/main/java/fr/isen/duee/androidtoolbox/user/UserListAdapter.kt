package fr.isen.duee.androidtoolbox.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.duee.androidtoolbox.R

class UserListAdapter(val user: UserList) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = user.results!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userListName?.text = user.results?.get(position)?.name?.first + " " + user.results?.get(position)?.name?.last
        holder.userListEmail?.text = user.results?.get(position)?.email
        holder.userListAddress?.text = user.results?.get(position)?.location?.street?.number + " " + user.results?.get(position)?.location?.street?.name + ", " + user.results?.get(position)?.location?.postcode + ", " + user.results?.get(position)?.location?.city + ", " + user.results?.get(position)?.location?.state + ", " + user.results?.get(position)?.location?.country
        Picasso.get().load( user.results?.get(position)?.picture?.large).into( holder.userListPicture);
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val userListName = itemView.findViewById<TextView>(R.id.userListName)
        val userListEmail = itemView.findViewById<TextView>(R.id.userListEmail)
        val userListAddress = itemView.findViewById<TextView>(R.id.userListAddress)
        val userListPicture = itemView.findViewById<ImageView>(R.id.userListPicture)
    }

}
