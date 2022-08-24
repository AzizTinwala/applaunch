package io.applaunch.applaunchmini.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User

class UserAdapter(var userList: MutableList<User>, var listener: OnClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text= userList[position].firstName
     holder.lastName.text= userList[position].lastName
     holder.email.text= userList[position].eMail
    }

    class ViewHolder(itemView: View, listener: OnClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var firstName: TextView
        var lastName: TextView
        var email: TextView

        init {
            firstName = itemView.findViewById(R.id.first_name)

            lastName = itemView.findViewById(R.id.last_name)

            email = itemView.findViewById(R.id.email)
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}