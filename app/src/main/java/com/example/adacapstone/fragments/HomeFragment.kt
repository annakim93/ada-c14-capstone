package com.example.adacapstone.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodels.ImgMsgViewModel
import com.example.adacapstone.adapters.ListImageAdapter
import com.example.adacapstone.data.viewmodels.IMCRelationsViewModel
import com.example.adacapstone.interfaces.Location
import com.example.adacapstone.utils.Permissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class HomeFragment() : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel
    private lateinit var mIMCRelationsViewModel: IMCRelationsViewModel
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Recyclerview setup
        mIMCRelationsViewModel = ViewModelProvider(this).get(IMCRelationsViewModel::class.java)
        getCurrentLatLong(requireContext(), requireActivity())
        val adapter = ListImageAdapter(
            mIMCRelationsViewModel,
            viewLifecycleOwner,
            mFusedLocationProviderClient,
            requireActivity()
        )
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

        gridBtn.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_manageGrid)
            Toast.makeText(
                    activity,
                    "Click on a photo to update or long-click to delete.",
                    Toast.LENGTH_SHORT
            ).show()
        }


//        getCurrentLatLong(requireContext(), requireActivity())
//        mImgMsgViewModel.currentLatitude.
//        Toast.makeText(context, "${latLongs[0]}", Toast.LENGTH_SHORT).show()
    }

    fun getCurrentLatLong(context: Context, activity: Activity) {
        val locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Permissions.LOCATION_REQUEST_CODE
            )
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

}
