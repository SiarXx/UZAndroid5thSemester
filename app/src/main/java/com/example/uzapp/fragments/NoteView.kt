package com.example.uzapp.fragments


import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import com.example.uzapp.R
import com.example.uzapp.models.Note
import kotlinx.android.synthetic.main.fragment_note_view.view.*
import java.io.*
import java.lang.Exception


class NoteView : Fragment(),View.OnClickListener {
    private lateinit var title: EditText
    private lateinit var noteBody: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_view, container, false)
        title = view.noteTitle
        noteBody = view.noteBody
        val saveNoteBtn = view.saveNote
        saveNoteBtn.setOnClickListener(this)
        return view
    }
    override fun onClick(view: View?){
        when(view!!.id){
            R.id.saveNote -> {
                Toast.makeText(context,"Saving",Toast.LENGTH_SHORT).show()
                saveNote(Note(title.text.toString(),noteBody.text.toString()))
            }
        }
    }
    private fun saveNote(note:Note){
        var filename = note.title
        val file = File(activity!!.filesDir,"Notes")
        if(!file.exists()){
            file.mkdir()
        }
        try {
            val gpxFile = File(file,filename + ".txt")
            val writer = FileWriter(gpxFile)
            writer.append(note.noteBody)
            writer.flush()
            writer.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
