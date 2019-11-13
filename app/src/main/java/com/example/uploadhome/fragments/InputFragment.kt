package com.example.uploadhome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.uploadhome.CommonViewModel
import com.example.uploadhome.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_input.*

class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
    }

    private fun initView(view : View) {
        val mViewModel = activity?.let {
            ViewModelProvider(activity!!).get(CommonViewModel::class.java)
        }

        button.setOnClickListener {
            if (isAllFilled(name, email, address)) {
                proceedToNextStep(mViewModel)
            } else {
                alertError()
            }
        }
    }

    private fun alertError() {
        Toast.makeText(context, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
    }

    private fun proceedToNextStep(viewModel: CommonViewModel?) {
        viewModel?.let {
            it.saveInfoStep1(inputName = name.text.toString(),
                inputEmail = email.text.toString(),
                inputAddress = address.text.toString(),
                inputNote = note.text.toString())
            it.navigate(R.id.photoFragment)
        }
    }


    private fun isAllFilled (vararg editText: TextInputEditText) : Boolean {
        var result = true
        loop@ for (txt in editText) {
            if (txt.text.isNullOrEmpty()) {
                result = false
                break@loop
            }
        }
        return result
    }
}