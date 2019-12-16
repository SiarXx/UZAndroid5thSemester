package com.example.uzapp.fragments


import android.app.AlertDialog
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
import com.example.uzapp.tools.Formatter
import kotlinx.android.synthetic.main.fragment_reminders.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Reminders : Fragment(), View.OnClickListener,ListOnClickListener, OnLongClickListener,TextWatcher{

    private val bundle = Bundle()
    var showedReminders = ArrayList<Reminder>()
    private lateinit var reminderAdapter: ReminderViewAdapter
    private lateinit var search: EditText
    var reminders = ArrayList<Reminder>()
    var archivedReminders = ArrayList<Reminder>()
    private val mapper = FileToReminderMapper()
    private val formatter = Formatter()
    private val sdf: SimpleDateFormat = SimpleDateFormat("ddMMyyyy")
    private var showingArchived = false

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

        //Reset Reminder List to show list of reminders not archived on start
        showedReminders.clear()
        reminders = listReminders()
        filterArchivedReminders(reminders)
        reminders.forEach {
            if(!it.archived){
                showedReminders.add(it)
            }
        }
        reminderAdapter.notifyDataSetChanged()

        //Fabs Click Listeners
        val addReminderBtn = view.addReminder
        val delRemindersBtn = view.deleteReminders
        val backRemindersBtn = view.backReminders
        val changeSetBtn = view.changeRemindersSet
        addReminderBtn.setOnClickListener(this)
        delRemindersBtn.setOnClickListener(this)
        backRemindersBtn.setOnClickListener(this)
        changeSetBtn.setOnClickListener(this)

        search = view.searchReminder
        search.addTextChangedListener(this)

        return view
    }
    private fun deleteStart(){
        AlertDialog.Builder(activity)
            .setTitle("Delete selected Reminders")
            .setMessage("Are you sure you want to delete selected Reminders?")
            .setPositiveButton("yes"){ _, _ ->
                deleteReminders()
            }
            .setNegativeButton("No",null).show()
    }
    private fun deleteReminders(){
        if (!reminders.any{it.selected}){
            Toast.makeText(context,"No notes selected",Toast.LENGTH_SHORT).show()
        }
        else{
            reminders.forEach {
                if(it.selected){
                    deleteReminderFile(it.reminderHour+it.reminderMinute+it.reminderDate+it.reminderTitle)
                }
                reminders = listReminders()
                updateShowedReminders(search.text)
            }
            Toast.makeText(context,"Delete successful", Toast.LENGTH_SHORT).show()
            reminderAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteReminderFile(reminderTitle: String?) {
        val file = File(activity!!.filesDir,"Reminders/" + reminderTitle + ".txt")
        file.delete()
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
            reminders.forEach {
                if(!it.archived){
                    showedReminders.add(it)
                }
            }
            reminderAdapter.notifyDataSetChanged()
        }else{
            showedReminders.clear()
            if(!showingArchived){
            reminders.forEach{ reminder ->
                if(reminder.reminderTitle!!.toLowerCase(Locale.getDefault()).contains(textToLower)){
                    showedReminders.add(reminder)
                }
            }
            reminderAdapter.notifyDataSetChanged()
        }
            else{
                archivedReminders.forEach{ reminder ->
                    if(reminder.reminderTitle!!.toLowerCase(Locale.getDefault()).contains(textToLower)){
                        showedReminders.add(reminder)
                    }
                }
                reminderAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun filterArchivedReminders(reminders:ArrayList<Reminder>){
        archivedReminders.clear()
        reminders.forEach {
            if(formatter.reminderDatetoComparableLong(it.reminderDate!!)<formatter.reminderDatetoComparableLong(sdf.format(Calendar.getInstance().time))){
                it.archived = true
                archivedReminders.add(it)
            }

        }
    }
    private fun changeSet(){
        if(!showingArchived){
            showedReminders.clear()
            showedReminders.addAll(archivedReminders)
            showingArchived = true
            Toast.makeText(context,"Showing Archived Reminders",Toast.LENGTH_SHORT).show()
            reminderAdapter.notifyDataSetChanged()
        }else{
            showedReminders.clear()
            reminders.forEach {
                if(!it.archived){
                    showedReminders.add(it)
                }
            }
            showingArchived = false
            Toast.makeText(context,"Showing Current Reminders",Toast.LENGTH_SHORT).show()
            reminderAdapter.notifyDataSetChanged()
        }
    }
    //Overrides
    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.addReminder -> Navigation.findNavController(view).navigate(R.id.action_reminders_to_reminderView)
            R.id.deleteReminders -> deleteStart()
            R.id.changeRemindersSet -> changeSet()
            R.id.backReminders -> Navigation.findNavController(view).popBackStack()
        }
    }
    override fun onClickNav(position: Int) {
        bundle.putSerializable("Reminder",showedReminders[position])
        Navigation.findNavController(view!!).navigate(R.id.action_reminders_to_reminderView,bundle)
    }
    override fun onLongClick(position: Int) {
        Toast.makeText(context,showedReminders[position].reminderTitle,Toast.LENGTH_SHORT).show()
            reminders.forEach {
                if (it.reminderTitle == showedReminders[position].reminderTitle) {
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
