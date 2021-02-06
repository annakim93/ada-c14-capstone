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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adacapstone.R
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.databinding.FragmentManageGridBinding
import com.example.adacapstone.utils.GridImageAdapter
import com.example.adacapstone.utils.ImgMsgListener

class ManageGrid : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    // Navigation
//    val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment) }
//    val localNavController: NavController? by lazy { view?.findNavController() }
    private lateinit var localNavController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentManageGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_grid, container, false)

        val application = requireNotNull(this.activity).application

        // Get reference to ViewModel associated with manage grid fragment
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        binding.imgMsgViewModel = mImgMsgViewModel

        val adapter = GridImageAdapter(ImgMsgListener { imgMsgId ->
            Toast.makeText(context, "You clicked $imgMsgId", Toast.LENGTH_LONG).show()
            mImgMsgViewModel.onImgMsgClicked(imgMsgId)
        })
        binding.recyclerViewManage.adapter = adapter

        mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        mImgMsgViewModel.navigateToUpdateFrag.observe(viewLifecycleOwner, Observer { imgMsg ->
            imgMsg?.let {
//                val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.local_nav_host_fragment) as? NavHostFragment
//                if (nestedNavHostFragment != null) {
//                    localNavController = nestedNavHostFragment.navController
//                }

//                localNavController?.navigate(
                this.findNavController().navigate(
                    ManageGridDirections.actionManageGridToUpdateFragment(imgMsg)
                )
                mImgMsgViewModel.onUpdateFragNavigated()
            }
        })

        // Grid setup
        val manager = GridLayoutManager(activity, 3)
        binding.recyclerViewManage.layoutManager = manager

        return binding.root
    }

}