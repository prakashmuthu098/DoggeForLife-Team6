package com.example.doggee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OtherUserAdapterClass(var UsersVal:MutableList<otherUserDataClass>, var context: Context):
    RecyclerView.Adapter<OtherUserAdapterClass.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OtherUserAdapterClass.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.other_user_list, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return UsersVal.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val otherUserEmail = itemView.findViewById<TextView>(R.id.otheruserEmail)
        val ownsIntrest = itemView.findViewById<TextView>(R.id.intrestPet)
        val ownEmail=itemView.findViewById<TextView>(R.id.fEmail)


    }

    override fun onBindViewHolder(holder: OtherUserAdapterClass.ViewHolder, position: Int) {
        holder.otherUserEmail.text="${UsersVal[position].email}"
        holder.ownsIntrest.text = "Interested in: ${UsersVal[position].reservationsAt}"

        val emailId=UsersVal[position].email.toString()
        val photoText = emailId.substring(0, 1)?.toUpperCase()
         holder.ownEmail.text=photoText+"!"



    }
}