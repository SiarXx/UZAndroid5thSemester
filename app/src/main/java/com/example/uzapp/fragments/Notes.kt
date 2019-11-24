package com.example.uzapp.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uzapp.R
import com.example.uzapp.adapters.NotesViewAdapter
import com.example.uzapp.models.Note
import com.example.uzapp.tools.FileToNoteMapper
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class Notes : Fragment(), NotesViewAdapter.ListOnClickListener,NotesViewAdapter.OnLongClickListener,View.OnClickListener,TextWatcher {

    private val bundle = Bundle()
    var showedNotes = ArrayList<Note>()
    private lateinit var notesAdapter: NotesViewAdapter
    private lateinit var search: EditText
    var notes = ArrayList<Note>()
    private val mapper = FileToNoteMapper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        //Inflate RecyclerView
        val notesRecyclerView = view.notesRecyclerView
        notesAdapter = NotesViewAdapter(showedNotes,this,this)
        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        //Adding Notes to test
        showedNotes.clear()
        notes = listNotes()
        showedNotes.addAll(notes)
        notesAdapter.notifyDataSetChanged()

        //Set listener on FAB's
        val addNoteBtn = view.addNote
        val deleteNotesBtn = view.deleteNotes
        deleteNotesBtn.setOnClickListener(this)
        addNoteBtn.setOnClickListener(this)

        search = view.search
        search.addTextChangedListener(this)
        return view
    }

    private fun deleteStart(){
        AlertDialog.Builder(activity)
            .setTitle("Delete selected Notes")
            .setMessage("Are you sure you want to delete selected notes?")
            .setPositiveButton("yes"){dialog, which ->
                deleteNotes()
            }
            .setNegativeButton("No",null).show()
    }
    private fun deleteNotes(){
        if (!notes.any{it.selected}){
            Toast.makeText(context,"No notes selected",Toast.LENGTH_SHORT).show()
        }
        else{
            notes.forEach {
                if(it.selected){
                    deleteNoteFile(it.title)
                }
                notes = listNotes()
                updateShowedNotes(search.text)
            }
            Toast.makeText(context,"Delete successful", Toast.LENGTH_SHORT).show()
            notesAdapter.notifyDataSetChanged()
        }
    }
    private fun deleteNoteFile(filename:String?){
        val file = File(activity!!.filesDir,"Notes/" + filename + ".txt")
        file.delete()
    }
    private fun listNotes():ArrayList<Note>{
        val directory = File(activity!!.filesDir,"Notes")
        val files: Array<File> = directory.listFiles()
        val notesTmp = ArrayList<Note>()
        files.forEach { notesTmp.add(mapper.mapNotes(it)) }
        return notesTmp
    }

    private fun updateShowedNotes(text: CharSequence?) {
        val textToLower = text.toString().toLowerCase(Locale.getDefault())
        if (text.isNullOrEmpty()) {
            showedNotes.clear()
            showedNotes.addAll(notes)
            notesAdapter.notifyDataSetChanged()
        } else {
            showedNotes.clear()
            notes.forEach { note ->
                if (note.title!!.toLowerCase(Locale.getDefault()).contains(textToLower))
                    showedNotes.add(note)
            }
            notesAdapter.notifyDataSetChanged()
        }
    }
    //Overrides
    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.addNote -> Navigation.findNavController(view).navigate(R.id.action_notes_to_noteView)
            R.id.deleteNotes -> deleteStart()
        }
    }
    override fun onClickNav(position: Int) {
        bundle.putSerializable("Note",showedNotes[position])
        Navigation.findNavController(view!!).navigate(R.id.action_notes_to_noteView,bundle)
    }
    override fun onLongClick(position: Int) {
        Toast.makeText(context,showedNotes[position].title,Toast.LENGTH_SHORT).show()
        notes.forEach {
            if(it.title == showedNotes[position].title){
                it.selected = !it.selected
            }
        }
    }
    override fun afterTextChanged(text: Editable?) {}
    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        updateShowedNotes(text)
    }




}
