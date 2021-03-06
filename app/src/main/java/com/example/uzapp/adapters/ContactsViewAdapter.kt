package com.example.uzapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uzapp.R
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.contacts_list_item.view.*


class ContactsViewAdapter(
    private val phoneBooks:ArrayList<PhoneBookEntity>,
    private val listOnClickListener: ListOnClickListener,
    private val listoOnLongClickListener: OnLongClickListener
):RecyclerView.Adapter<ContactsViewAdapter.ContactsViewHolder>(){


    inner class ContactsViewHolder(container: View): RecyclerView.ViewHolder(container){
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
        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item,parent,false))
    }

    override fun getItemCount(): Int = phoneBooks.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val phoneBook = phoneBooks[position]
        holder.name.text = phoneBook.name
        holder.lastname.text = phoneBook.lastName
        holder.email.text = phoneBook.email
        holder.number.text = phoneBook.number
        if(phoneBook.avatar.isNullOrEmpty()){
            if(phoneBook.sex.equals("Male"))
                Picasso.get().load(R.drawable.baklazan).resize(120,120).into(holder.avatar)
            else if(phoneBook.sex.equals("Female"))
                Picasso.get().load(R.drawable.female).resize(120,120).into(holder.avatar)
        }else{
            Picasso.get().load(R.drawable.mleko).resize(120,120).into(holder.avatar)
        }

    }
}