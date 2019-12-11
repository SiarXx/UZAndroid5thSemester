package com.example.uzapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uzapp.R
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.models.Note
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesViewAdapter(
    private val notes: ArrayList<Note>,
    private val listOnClickListener: ListOnClickListener,
    private val listOnLongClickListener: OnLongClickListener
) : RecyclerView.Adapter<NotesViewAdapter.NotesViewHolder>()
{

    inner class NotesViewHolder(container: View) : RecyclerView.ViewHolder(container) {

        val noteTitle: TextView = container.listTitle
        val noteHighlight: TextView = container.listHighlight
        val layout:LinearLayout = container.recyclerLayout

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_list_item,parent,false))
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.noteTitle.text = note.title
        if(note.noteBody!!.length > 50){
            holder.noteHighlight.text = note.noteBody.substring(0,47) + "..."
        }
        else{
            holder.noteHighlight.text = note.noteBody
        }
        if(note.selected){
            holder.layout.setBackgroundColor(Color.BLUE)
        }
        else{
            holder.layout.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}


