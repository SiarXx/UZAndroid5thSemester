package com.example.uzapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.uzapp.R
import com.example.uzapp.models.Reminder
import kotlinx.android.synthetic.main.fragment_reminder_view.view.*
import java.io.File
import java.io.FileWriter
import java.lang.NullPointerException


class ReminderView : Fragment(),View.OnClickListener {


    private lateinit var title: EditText
    private lateinit var body: EditText
    private lateinit var selectedReminder: Reminder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedReminder = try {
            arguments!!.getSerializable("Reminder") as Reminder
        }catch (e: NullPointerException){
            Reminder()
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reminder_view, container, false)
        title = view.reminderViewTitle
        body = view.reminderViewBody

        //disable title change on loading reminder
        if(!selectedReminder.reminderTitle.isNullOrEmpty()){
            title.setText(selectedReminder.reminderTitle)
            body.setText(selectedReminder.reminderBody)
            title.isFocusable = false
            title.isClickable = false
        }

        //set Listeners on Fabs
        val saveReminderBtn = view.saveReminder
        val backReminderViewBtn = view.backReminderView
        saveReminderBtn.setOnClickListener(this)
        backReminderViewBtn.setOnClickListener(this)
        return view
    }


    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.saveReminder -> {
                saveReminder(Reminder("120001012019" + title.text,body.text.toString()))
                Toast.makeText(context,"Note saved successful", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).popBackStack()
            }
            R.id.backReminderView -> Navigation.findNavController(view).popBackStack()
        }
    }
    private fun saveReminder(reminder:Reminder){
        val filename = reminder.reminderTitle
        val file = File(activity!!.filesDir,"Reminders")
        if(!file.exists()){
            file.mkdir()
        }
        try {
            val gpxFile = File(file,filename + ".txt")
            val writer = FileWriter(gpxFile)
            writer.append(reminder.reminderBody)
            writer.flush()
            writer.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}
