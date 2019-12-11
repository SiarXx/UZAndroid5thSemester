package com.example.uzapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.uzapp.R
import kotlinx.android.synthetic.main.fragment_reminders.view.*

/**
 * A simple [Fragment] subclass.
 */
class Reminders : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)
        val addReminderBtn = view.addReminder
        addReminderBtn.setOnClickListener(this)
        return view
    }
    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.addReminder -> Navigation.findNavController(view).navigate(R.id.action_reminders_to_reminderView)
            //R.id.deleteNotes -> deleteStart()
            R.id.backNotes -> Navigation.findNavController(view).popBackStack()
        }
    }

}
