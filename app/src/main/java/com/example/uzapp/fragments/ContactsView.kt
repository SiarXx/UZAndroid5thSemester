package com.example.uzapp.fragments


import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.uzapp.MainActivity

import com.example.uzapp.R
import com.example.uzapp.database.AppDatabase
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.models.PhoneBook
import com.example.uzapp.tools.DBWorkerThread
import kotlinx.android.synthetic.main.fragment_contacts_view.view.*

class ContactsView : Fragment(),View.OnClickListener {


    private lateinit var selectedPhoneBook: PhoneBookEntity
    private lateinit var  name: EditText
    private lateinit var  lastname: EditText
    private lateinit var  mail: EditText
    private lateinit var  number: EditText
    private lateinit var  avatar: EditText
    private lateinit var  sex: Spinner
    private lateinit var dB: AppDatabase
    private lateinit var worker: DBWorkerThread
    private var loadedContact = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            selectedPhoneBook = arguments!!.getSerializable("Contact") as PhoneBookEntity
            loadedContact = true
        }catch (e:NullPointerException){
            selectedPhoneBook = PhoneBookEntity()

        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts_view, container, false)
        dB = (activity as MainActivity).mDb!!
        worker = (activity as MainActivity).dbWorker
        name = view.CVName
        lastname = view.CVLastName
        mail = view.CVMail
        number = view.CVNumber
        avatar = view.CVAvatar
        sex = view.CVSex
        if(loadedContact){
            name.setText(selectedPhoneBook.name)
            lastname.setText(selectedPhoneBook.lastName)
            mail.setText(selectedPhoneBook.email)
            number.setText(selectedPhoneBook.number)
            avatar.setText(selectedPhoneBook.avatar)
            if(selectedPhoneBook.sex.equals("Male"))
                sex.setSelection(0)
            else
                sex.setSelection(1)
        }

        val saveContactBtn = view.saveContact
        val backContactsViewBtn = view.backContactsView
        saveContactBtn.setOnClickListener(this)
        backContactsViewBtn.setOnClickListener(this)

        return view
    }
    private fun saveContact() {
        var contactToSave: PhoneBookEntity
        if(!loadedContact){
         contactToSave= PhoneBookEntity(
             name = name.text.toString(),
             lastName = lastname.text.toString(),
             email = mail.text.toString(),
             number = number.text.toString(),
             avatar = avatar.text.toString(),
             sex = sex.selectedItem.toString())
        worker.postTask( Runnable { dB!!.PhoneBookDao().insertPhoneBooks(contactToSave) })
            Toast.makeText(context,"Contact Added",Toast.LENGTH_SHORT).show()
        }
        else{
            worker.postTask( Runnable { dB.PhoneBookDao().updatePhoneBooks(
                selectedPhoneBook.id,
                name.text.toString(),
                lastname.text.toString(),
                sex.selectedItem.toString(),
                number.text.toString(),
                mail.text.toString(),
                avatar.text.toString()
            )})
            Toast.makeText(context,"Changes Saved",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.saveContact -> {
                saveContact()
                Navigation.findNavController(view).popBackStack()
            }
            R.id.backContactsView -> Navigation.findNavController(view).popBackStack()
        }
    }



}
