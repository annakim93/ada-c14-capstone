package com.example.adacapstone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.ImgMsgViewModel
import com.example.adacapstone.utils.ListImageAdapter

class HomeFragment : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Recyclerview setup
        val adapter = ListImageAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ImgMsgModel
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer { imgMsg ->
            adapter.setData(imgMsg)
        })

        return view
    }

}