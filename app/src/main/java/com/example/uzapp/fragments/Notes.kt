package com.example.uzapp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uzapp.R
import com.example.uzapp.adapters.NotesViewAdapter
import com.example.uzapp.models.Note
import kotlinx.android.synthetic.main.fragment_notes.view.*


class Notes : Fragment(), NotesViewAdapter.ListOnClickListener,NotesViewAdapter.OnLongClickListener,View.OnClickListener {


    private val bundle = Bundle()
    private lateinit var selectedNotes: ArrayList<Int>
    private lateinit var notesAdapter: NotesViewAdapter
    var notes = ArrayList<Note>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        //Inflate RecyclerView
        val notesRecyclerView = view.notesRecyclerView
        notesAdapter = NotesViewAdapter(notes,this,this)
        selectedNotes = ArrayList()
        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        //Adding Notes to test
        notes.add(Note("test","test"))
        notesAdapter.notifyDataSetChanged()

        //Set listener on FAB's
        val addNoteBtn = view.addNote
        val deleteNotesBtn = view.deleteNotes
        deleteNotesBtn.setOnClickListener(this)
        addNoteBtn.setOnClickListener(this)

        return view
    }

    override fun onClickNav(position: Int) {
        bundle.putSerializable("Note",notes[position])
        Navigation.findNavController(view!!).navigate(R.id.action_notes_to_noteView,bundle)
    }
    override fun onLongClick(position: Int) {
        Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
        selectedNotes.add(position)

    }
    fun deleteNotes(){
        if (selectedNotes.size==0){
            Toast.makeText(context,"No notes selected",Toast.LENGTH_SHORT).show()
        }
        else{
            selectedNotes.forEach { selected ->
                notes.removeAt(selected)
            }
            Toast.makeText(context,"Delete successful", Toast.LENGTH_SHORT).show()
            selectedNotes.clear()
            notesAdapter.notifyDataSetChanged()
        }
    }
    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.addNote -> Navigation.findNavController(view).navigate(R.id.action_notes_to_noteView)
            R.id.deleteNotes -> deleteNotes()
        }
    }

}
