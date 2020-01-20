package com.example.uzapp.fragments


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.uzapp.R
import kotlinx.android.synthetic.main.fragment_multimedia.view.*


class Multimedia : Fragment(),View.OnClickListener {


    private val browserIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wiea.uz.zgora.pl/"))
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_multimedia, container, false)

        val wieaBtn = view.WieaBtn
        wieaBtn.setOnClickListener(this)

        return view
    }

    private fun nuclearLaunch() {
        AlertDialog.Builder(activity)
            .setTitle("Nuclear Launch Initiated")
            .setMessage("Do you want to continue with elimination of a humankind?")
            .setPositiveButton("I just want to watch the world burn"){_,_ ->
                goToWiea()
            }
            .setNegativeButton("No, I am scared",null).show()
    }

    private fun goToWiea() {
        startActivity(browserIntent)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.WieaBtn -> nuclearLaunch()
        }
    }
}
