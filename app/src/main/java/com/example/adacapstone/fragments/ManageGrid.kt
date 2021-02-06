package com.example.adacapstone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adacapstone.R
import com.example.adacapstone.data.ImgMsgDatabase
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.databinding.FragmentManageGridBinding
import com.example.adacapstone.utils.GridImageAdapter
import com.example.adacapstone.utils.ImgMsgListener

class ManageGrid : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentManageGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_grid, container, false)

        val application = requireNotNull(this.activity).application

        // Get refernce to ViewModel associated with manage grid fragment
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        binding.imgMsgViewModel = mImgMsgViewModel

        val adapter = GridImageAdapter(ImgMsgListener { imgMsgId ->
            Toast.makeText(context, "You clicked $imgMsgId", Toast.LENGTH_LONG).show()
        })
        binding.recyclerViewManage.adapter = adapter

        mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
                adapter.submitList(it)
//                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
            }
        })

        binding.lifecycleOwner = this

        // Grid setup
        val manager = GridLayoutManager(activity, 3)
        binding.recyclerViewManage.layoutManager = manager

        return binding.root
    }

}