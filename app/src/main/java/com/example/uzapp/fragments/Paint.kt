package com.example.uzapp.fragments


import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import com.example.uzapp.MainActivity

import com.example.uzapp.R
import com.example.uzapp.tools.PaintView
import kotlinx.android.synthetic.main.fragment_paint.view.*

/**
 * A simple [Fragment] subclass.
 */
class Paint : Fragment() {

    lateinit var paintView: PaintView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paint, container, false)

        setHasOptionsMenu(true)

        paintView = view.paintView
        var metrics = DisplayMetrics()

        val act = activity as MainActivity

        act.windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)

        view.redBtn.setOnClickListener {
            paintView.red()
        }
        view.greenBtn.setOnClickListener {
            paintView.green()
        }
        view.blueBtn.setOnClickListener {
            paintView.blue()
        }
        view.orangeBtn.setOnClickListener {
            paintView.orange()
        }
        view.blackBtn.setOnClickListener {
            paintView.black()
        }
        view.size20Btn.setOnClickListener {
            paintView.size20()
        }
        view.size30Btn.setOnClickListener {
            paintView.size30()
        }
        view.size40Btn.setOnClickListener {
            paintView.size40()
        }
        view.size50Btn.setOnClickListener {
            paintView.size50()
        }
        view.size60Btn.setOnClickListener {
            paintView.size60()
        }

        return view
    }
}
