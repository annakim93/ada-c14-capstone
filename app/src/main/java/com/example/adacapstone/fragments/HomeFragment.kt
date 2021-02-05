package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
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

        // Click listener for manage photo-msgs
        val gridBtn: ImageView = view.findViewById(R.id.manage_grid_btn)
        gridBtn.setOnClickListener {
            val fragment: Fragment = ManageGrid()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            Toast.makeText(activity, "Click on a photo to update or delete.", Toast.LENGTH_LONG).show()
        }

        return view
    }

}