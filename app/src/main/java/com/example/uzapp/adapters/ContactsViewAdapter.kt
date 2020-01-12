package com.example.uzapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uzapp.R
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.models.PhoneBook
import com.example.uzapp.tools.Formatter
import kotlinx.android.synthetic.main.contacts_list_item.view.*


class ContactsViewAdapter(
    private val phoneBooks:ArrayList<PhoneBookEntity>,
    private val listOnClickListener: ListOnClickListener,
    private val listoOnLongClickListener: OnLongClickListener
):RecyclerView.Adapter<ContactsViewAdapter.ContactsViewHolder>(){


    inner class ContactsViewHolder(container: View): RecyclerView.ViewHolder(container){
        val layout : ConstraintLayout = container.contactsItemLayout
        val name: TextView = container.contactsName
        val lastname: TextView = container.contactsLastname
        val email: TextView = container.contactsEmail
        val number: TextView = container.contactsNumber
        val avatar: ImageView = container.contactsAvatar

        init {
            container.setOnClickListener {
                listOnClickListener.onClickNav(adapterPosition)
            }
            container.setOnLongClickListener{
                listoOnLongClickListener.onLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reminder_list_item,parent,false))
    }

    override fun getItemCount(): Int = phoneBooks.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val formatter: Formatter = Formatter()
        val phoneBook = phoneBooks[position]
        holder.name.text = phoneBook.name
        holder.lastname.text = phoneBook.lastName
        holder.email.text = phoneBook.email
        holder.number.text = phoneBook.number
        if(false){
            holder.layout.setBackgroundColor(Color.BLUE)
        }
        else{
            holder.layout.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}