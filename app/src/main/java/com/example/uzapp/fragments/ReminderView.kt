package com.example.uzapp.fragments


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation

import com.example.uzapp.R
import com.example.uzapp.models.Reminder
import com.example.uzapp.tools.Formatter
import kotlinx.android.synthetic.main.fragment_reminder_view.*
import kotlinx.android.synthetic.main.fragment_reminder_view.view.*
import java.io.File
import java.io.FileWriter
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*





class ReminderView : Fragment(),View.OnClickListener,CalendarView.OnDateChangeListener {



    private lateinit var title: EditText
    private lateinit var body: EditText
    private lateinit var selectedReminder: Reminder
    private lateinit var hourPicker: TextView
    private lateinit var reminderSelectedDate: String
    private val formatter = Formatter()
    private val sdf:SimpleDateFormat = SimpleDateFormat("ddMMyyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reminder_view, container, false)
        val calendarView = view.calendarView
        selectedReminder = try {
            arguments!!.getSerializable("Reminder") as Reminder
        }catch (e: NullPointerException){
            Reminder()
        }
        title = view.reminderViewTitle
        body = view.reminderViewBody
        hourPicker = view.selectHour
        reminderSelectedDate = sdf.format(calendarView.date).toString()
        calendarView.setOnDateChangeListener(this)
        //Log.d("data", calendarView.date.toString())

        //disable title change on loading reminder and fill view with reminder data
        if(!selectedReminder.reminderTitle.isNullOrEmpty()){
            title.setText(selectedReminder.reminderTitle)
            body.setText(selectedReminder.reminderBody)
            calendarView.setDate(sdf.parse(selectedReminder.reminderDate).time)
            hourPicker.text = (selectedReminder.reminderHour + ":" + selectedReminder.reminderMinute)
            title.isFocusable = false
            title.isClickable = false
        }

        //set Listeners on Fabs and EditText
        val saveReminderBtn = view.saveReminder
        val backReminderViewBtn = view.backReminderView
        saveReminderBtn.setOnClickListener(this)
        backReminderViewBtn.setOnClickListener(this)
        hourPicker.setOnClickListener(this)
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
    private fun deleteOldReminder(reminder: Reminder) {
        val file = File(activity!!.filesDir,"Reminders/" +formatter.reminderToString(reminder) + ".txt")
        file.delete()
    }
    private fun pickHour() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            hourPicker.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }
    private fun setupAlarm(){
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calAlarm = Calendar.getInstance()
        val intent = Intent()
        intent.putExtra("title","dupa")
        val pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_ONE_SHOT)
        calAlarm.set(2019,11,15,23,31)
        alarmManager.set(AlarmManager.RTC_WAKEUP,calAlarm.timeInMillis,pendingIntent)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.saveReminder -> {
                if(!selectedReminder.reminderTitle.isNullOrEmpty()){
                    deleteOldReminder(selectedReminder)
                }
                saveReminder(Reminder(hourPicker.text.toString().replace(":","") + reminderSelectedDate + title.text,body.text.toString()))
                Toast.makeText(context,sdf.format(calendarView.date).toString(), Toast.LENGTH_LONG).show()
                setupAlarm()
                Navigation.findNavController(view).popBackStack()
            }
            R.id.backReminderView -> Navigation.findNavController(view).popBackStack()
            R.id.selectHour -> pickHour()
        }
    }



    override fun onSelectedDayChange(cView: CalendarView, year: Int, month: Int, day: Int) {
        reminderSelectedDate = formatter.calendarViewDataToReminderDate(day,month,year)
    }
}
