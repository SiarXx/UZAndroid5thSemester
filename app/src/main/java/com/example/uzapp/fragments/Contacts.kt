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
import com.example.uzapp.MainActivity
import com.example.uzapp.R
import com.example.uzapp.adapters.ContactsViewAdapter
import com.example.uzapp.database.AppDatabase
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.tools.DBWorkerThread
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import java.util.*
import kotlin.collections.ArrayList

class Contacts : Fragment(), ListOnClickListener, OnLongClickListener,View.OnClickListener,
    TextWatcher {
    val bundle = Bundle()
    var contactsList = ArrayList<PhoneBookEntity>()
    private lateinit var  dB : AppDatabase
    private lateinit var worker : DBWorkerThread
    private lateinit var phoneBookAdapter : ContactsViewAdapter
    private lateinit var search : EditText
    private var delMode = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        val phoneBookRecyclerView = view.contactsRecyclerView
        phoneBookAdapter = ContactsViewAdapter(contactsList,this,this)
        phoneBookRecyclerView.adapter = phoneBookAdapter
        phoneBookRecyclerView.layoutManager = LinearLayoutManager(context)
        dB = (activity as MainActivity).mDb!!
        worker = (activity as MainActivity).dbWorker
        refreshList()

        search = view.search
        val backBtn = view.backContacts
        val delBtn = view.deleteContacts
        val addBtn = view.addContact
        backBtn.setOnClickListener(this)
        delBtn.setOnClickListener(this)
        addBtn.setOnClickListener(this)
        search.addTextChangedListener(this)

        return view
    }

    private fun deleteModeChange() {
        delMode = !delMode
        if(delMode){
            Toast.makeText(context,"Delete mode enabled",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Delete mode disabled",Toast.LENGTH_SHORT).show()
        }
    }
    private fun refreshList(){
        worker.postTask( Runnable {
            contactsList.clear()
            contactsList.addAll(dB.PhoneBookDao().selectAll())
            activity!!.runOnUiThread{
                phoneBookAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun updateShowedContacts(text: CharSequence?) {
        val textToLower = text.toString().toLowerCase(Locale.getDefault())
        if (text.isNullOrEmpty()) {
            refreshList()
        }
        else{
            worker.postTask( Runnable {
                contactsList.clear()
                contactsList.addAll(dB.PhoneBookDao().findMatchingNumber("%$textToLower%"))
                activity!!.runOnUiThread{
                    phoneBookAdapter.notifyDataSetChanged()
                }
            })
        }
    }
    //overrides
    override fun onClickNav(position: Int) {
        if(delMode){
            deleteStart(position)
        }
        else{
            bundle.putSerializable("Contact", contactsList[position])
            Navigation.findNavController(view!!).navigate(R.id.action_contacts_to_contactsView,bundle)
        }

    }

    private fun deleteStart(position: Int) {
        AlertDialog.Builder(activity)
            .setTitle("Delete this Contact?")
            .setMessage("Are you sure you want to delete this contact?")
            .setPositiveButton("yes"){ _, _ ->
                deleteContact(position)
            }
            .setNegativeButton("No",null).show()

    }

    private fun deleteContact(position: Int) {
        worker.postTask( Runnable { dB.PhoneBookDao().deletePhoneBook(contactsList[position].id)})
        refreshList()
    }

    override fun onLongClick(position: Int) {
        Toast.makeText(context,"Am I a LongClick Joke to You???",Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.backContacts -> Navigation.findNavController(v).popBackStack()
            R.id.addContact -> Navigation.findNavController(view!!).navigate(R.id.action_contacts_to_contactsView)
            R.id.deleteContacts -> deleteModeChange()
        }
    }



    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        updateShowedContacts(text)
    }



}
