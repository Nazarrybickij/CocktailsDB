package com.nazarrybickij.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nazarrybickij.myapplication.viewmodels.AssistantViewModel
import com.nazarrybickij.myapplication.R

class AssistantFragment : Fragment() {

    companion object {
        fun newInstance() = AssistantFragment()
    }

    private lateinit var viewModel: AssistantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.assistant_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AssistantViewModel::class.java)
        // TODO: Use the ViewModel
    }

}