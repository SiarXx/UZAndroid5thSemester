package com.example.uzapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotesViewAdapter(
    private val listOnClickListener: ListOnClickListener
) : RecyclerView.Adapter<NotesViewAdapter.MyViewHolder>()
{


    interface ListOnClickListener {
    fun onClickNav(position: Int)
}
    inner class MyViewHolder(container: View) : RecyclerView.ViewHolder(container) {


        init {
            container.setOnClickListener {
                listOnClickListener.onClickNav(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


