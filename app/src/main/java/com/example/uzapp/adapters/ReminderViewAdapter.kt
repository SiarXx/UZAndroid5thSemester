package com.example.uzapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uzapp.R
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.models.Reminder
import com.example.uzapp.tools.Formatter
import kotlinx.android.synthetic.main.reminder_list_item.view.*

class ReminderViewAdapter(
    private val reminders:ArrayList<Reminder>,
    private val listOnClickListener: ListOnClickListener,
    private val listoOnLongClickListener: OnLongClickListener
):RecyclerView.Adapter<ReminderViewAdapter.ReminderViewHolder>(){


    inner class ReminderViewHolder(container: View): RecyclerView.ViewHolder(container){
        val reminderTitle: TextView = container.reminderTitle
        val reminderHighlight : TextView = container.reminderHighlight
        val reminderDate: TextView = container.reminderDate
        val reminderHour: TextView = container.reminderHour
        val layout: ConstraintLayout = container.reminderItemLayout

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reminder_list_item,parent,false))
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val formatter: Formatter = Formatter()
        val reminder = reminders[position]
        holder.reminderTitle.text = reminder.reminderTitle
        holder.reminderDate.text = formatter.stringToReminderDate(reminder.reminderDate!!)
        holder.reminderHour.text = reminder.reminderHour
        if(reminder.reminderBody!!.length > 50){
            holder.reminderHighlight.text = reminder.reminderBody.substring(0,47) + "..."
        }
        else{
            holder.reminderHighlight.text = reminder.reminderBody
        }
        if(reminder.selected){
            holder.layout.setBackgroundColor(Color.BLUE)
        }
        else{
            holder.layout.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}