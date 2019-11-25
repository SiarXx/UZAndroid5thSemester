package com.example.uzapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uzapp.R
import com.example.uzapp.models.Note
import kotlinx.android.synthetic.main.fragment_note_view.view.*
import java.io.File
import java.io.FileWriter


class NoteView : Fragment(),View.OnClickListener {
    private lateinit var title: EditText
    private lateinit var noteBody: EditText
    private lateinit var selectedNote : Note
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedNote = try {
            arguments!!.getSerializable("Note") as Note
        }catch (e:NullPointerException) {
            Note()
        }
        val view = inflater.inflate(R.layout.fragment_note_view, container, false)
        title = view.noteTitle
        noteBody = view.noteBody
        if(!selectedNote.title.isNullOrEmpty()){
            title.setText(selectedNote.title)
            noteBody.setText(selectedNote.noteBody)
            title.isFocusable = false
            title.isClickable = false
        }

        val saveNoteBtn = view.saveNote
        val backBtn = view.backNoteView
        saveNoteBtn.setOnClickListener(this)
        backBtn.setOnClickListener(this)
        return view
    }
    override fun onClick(view: View?){
        when(view!!.id){
            R.id.saveNote -> {
                saveNote(Note(title.text.toString(),noteBody.text.toString()))
                Toast.makeText(context,"Note saved successful",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).popBackStack()
            }
            R.id.backNoteView -> Navigation.findNavController(view).popBackStack()
        }
    }
    private fun saveNote(note:Note){
        val filename = note.title
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
