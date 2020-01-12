package com.example.uzapp.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.uzapp.R
import com.example.uzapp.adapters.ContactsViewAdapter
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.interfaces.ListOnClickListener
import com.example.uzapp.interfaces.OnLongClickListener
import com.example.uzapp.models.PhoneBook
import com.example.uzapp.viewmodels.PhoneBookViewModel
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class Contacts : Fragment(), ListOnClickListener, OnLongClickListener,View.OnClickListener,
    TextWatcher {


    val bundle = Bundle()
    val contactsList = ArrayList<PhoneBookEntity>()
    private lateinit var phoneBookAdapter : ContactsViewAdapter
    private lateinit var search : EditText
    val viewModel: PhoneBookViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        val phoneBookRecyclerView = view.contactsRecyclerView
        phoneBookAdapter = ContactsViewAdapter(contactsList,this,this)
        viewModel.getAllPhoneBooks().observe(this, Observer {
            contactsList.addAll(it)
            phoneBookAdapter.notifyDataSetChanged()
        })
        return view
    }

    override fun onClickNav(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun afterTextChanged(s: Editable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
