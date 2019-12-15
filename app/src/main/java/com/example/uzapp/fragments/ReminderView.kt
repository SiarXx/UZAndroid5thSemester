package com.example.uzapp.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.uzapp.R
import com.example.uzapp.models.Reminder
import kotlinx.android.synthetic.main.fragment_reminder_view.*
import kotlinx.android.synthetic.main.fragment_reminder_view.view.*
import java.io.File
import java.io.FileWriter
import java.lang.NullPointerException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class ReminderView : Fragment(),View.OnClickListener {


    private lateinit var title: EditText
    private lateinit var body: EditText
    private lateinit var selectedReminder: Reminder
    private val sdf:SimpleDateFormat = SimpleDateFormat("ddMMyyyy")

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
        val calendarView = view.calendarView
        //Log.d("data", calendarView.date.toString())

        //disable title change on loading reminder
        if(!selectedReminder.reminderTitle.isNullOrEmpty()){
            title.setText(selectedReminder.reminderTitle)
            body.setText(selectedReminder.reminderBody)
            calendarView.setDate(sdf.parse(selectedReminder.reminderDate).time)
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

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.saveReminder -> {
                saveReminder(Reminder("1200" + sdf.format(calendarView.date).toString() + title.text,body.text.toString()))
                Toast.makeText(context,sdf.format(calendarView.date).toString(), Toast.LENGTH_LONG).show()
                Navigation.findNavController(view).popBackStack()
            }
            R.id.backReminderView -> Navigation.findNavController(view).popBackStack()
        }
    }
}
