package com.example.uzapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.uzapp.R
import kotlinx.android.synthetic.main.fragment_main_menu.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainMenu : Fragment(),View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        view.notesBtn.setOnClickListener(this)
        view.reminderBtn.setOnClickListener(this)
        view.contactsBtn.setOnClickListener(this)
        view.multimediaBtn.setOnClickListener(this)
        return view
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.notesBtn -> Navigation.findNavController(v).navigate(R.id.action_mainMenu_to_notes)
            R.id.reminderBtn -> Navigation.findNavController(v).navigate(R.id.action_mainMenu_to_reminders)
            R.id.contactsBtn -> Navigation.findNavController(v).navigate(R.id.action_mainMenu_to_contacts)
            R.id.multimediaBtn -> Navigation.findNavController(v).navigate(R.id.action_mainMenu_to_multimedia)

        }

    }


}
