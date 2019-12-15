package com.example.uzapp.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.uzapp.R
import com.example.uzapp.adapters.ReminderViewAdapter
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.models.Reminder
import com.example.uzapp.tools.FileToReminderMapper
import kotlinx.android.synthetic.main.fragment_reminders.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class Reminders : Fragment(), View.OnClickListener,ListOnClickListener, OnLongClickListener,TextWatcher{

    private val bundle = Bundle()
    var showedReminders = ArrayList<Reminder>()
    private lateinit var reminderAdapter: ReminderViewAdapter
    private lateinit var search: EditText
    var reminders = ArrayList<Reminder>()
    private val mapper = FileToReminderMapper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)
        val remindersRecyclerView = view.remindersRecyclerView
        reminderAdapter = ReminderViewAdapter(showedReminders,this,this)
        remindersRecyclerView.adapter = reminderAdapter
        remindersRecyclerView.layoutManager = LinearLayoutManager(context)

        //Reset Reminder List to show full list of reminders on start
        showedReminders.clear()
        reminders = listReminders()
        showedReminders.addAll(reminders)
        reminderAdapter.notifyDataSetChanged()

        //Fabs Click Listeners
        val addReminderBtn = view.addReminder
        val delRemindersBtn = view.deleteReminders
        val backRemindersBtn = view.backReminders
        addReminderBtn.setOnClickListener(this)
        delRemindersBtn.setOnClickListener(this)
        backRemindersBtn.setOnClickListener(this)

        search = view.searchReminder
        search.addTextChangedListener(this)

        return view
    }
    private fun deleteStart(){
        Toast.makeText(context,"Delete Reminders TODO",Toast.LENGTH_SHORT).show()
    }
    private fun listReminders():ArrayList<Reminder>{
        val directory = File(activity!!.filesDir,"Reminders")
        if(!directory.exists()){
            directory.mkdir()
        }
        val files: Array<File> = directory.listFiles()
        val remindersTmp = ArrayList<Reminder>()
        files.forEach { remindersTmp.add(mapper.mapReminders(it)) }
        return remindersTmp
    }
    private fun updateShowedReminders(text: CharSequence?){
        val textToLower = text.toString().toLowerCase(Locale.getDefault())
        if(text.isNullOrEmpty()){
            showedReminders.clear()
            showedReminders.addAll(reminders)
            reminderAdapter.notifyDataSetChanged()
        }else{
            showedReminders.clear()
            reminders.forEach{ reminder ->
                if(reminder.reminderTitle!!.toLowerCase(Locale.getDefault()).contains(textToLower)){
                    showedReminders.add(reminder)
                }
            }
            reminderAdapter.notifyDataSetChanged()
        }
    }
    //Overrides
    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.addReminder -> Navigation.findNavController(view).navigate(R.id.action_reminders_to_reminderView)
            R.id.deleteReminders -> deleteStart()
            R.id.backReminders -> Navigation.findNavController(view).popBackStack()
        }
    }
    override fun onClickNav(position: Int) {
        bundle.putSerializable("Reminder",showedReminders[position])
        Navigation.findNavController(view!!).navigate(R.id.action_reminders_to_reminderView,bundle)
    }
    override fun onLongClick(position: Int) {
        Toast.makeText(context,showedReminders[position].reminderTitle,Toast.LENGTH_SHORT).show()
        reminders.forEach{
            if(it.reminderTitle == showedReminders[position].reminderTitle){
                it.selected = !it.selected
            }
        }
        reminderAdapter.notifyDataSetChanged()
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        updateShowedReminders(text)
    }



}
