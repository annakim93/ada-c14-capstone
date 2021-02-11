package com.example.adacapstone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.MainActivity
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.utils.ListImageAdapter

class HomeFragment : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener to navigate to manage photo-msgs fragment
        val navController: NavController = Navigation.findNavController(view)
        val gridBtn: ImageView = view.findViewById(R.id.manage_grid_btn)

        gridBtn.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_manageGrid)
            Toast.makeText(activity, "Click on a photo to update or long-click to delete.", Toast.LENGTH_LONG).show()
        }

    }

}
