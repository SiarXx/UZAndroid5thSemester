package com.example.uzapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uzapp.R
import com.example.uzapp.models.Note
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesViewAdapter(
    private val notes: ArrayList<Note>,
    private val listOnClickListener: ListOnClickListener,
    private val listOnLongClickListener: OnLongClickListener
) : RecyclerView.Adapter<NotesViewAdapter.MyViewHolder>()
{


    interface ListOnClickListener {
    fun onClickNav(position: Int)
}
    interface OnLongClickListener {
        fun onLongClick(position: Int)
    }
    inner class MyViewHolder(container: View) : RecyclerView.ViewHolder(container) {

        val noteTitle: TextView = container.listTitle
        init {
            container.setOnClickListener {
                listOnClickListener.onClickNav(adapterPosition)
            }
            container.setOnLongClickListener{
                listOnLongClickListener.onLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_list_item,parent,false))
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes[position]
        holder.noteTitle.text = note.title
    }
}


