package com.example.adacapstone.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodels.ImgMsgViewModel
import com.example.adacapstone.databinding.FragmentManageGridBinding
import com.example.adacapstone.adapters.GridImageAdapter
import com.example.adacapstone.adapters.ImgMsgListener

class ManageGridFragment : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel
    private lateinit var adapter: GridImageAdapter
    private lateinit var navController: NavController

    // Action mode vars for mutliple selection
    private var isActionMode = false
    private val selectedItems = arrayListOf<ImageMessage>()
    private var counter = 0
    private lateinit var toolbarHeaderTxt: TextView
    private lateinit var deleteBtn: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentManageGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_grid, container, false)

        // Get reference to ViewModel associated with manage grid fragment
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        binding.imgMsgViewModel = mImgMsgViewModel

        adapter = GridImageAdapter(ImgMsgListener { imgMsgId ->
            mImgMsgViewModel.onImgMsgClicked(imgMsgId)
        }, this)
        binding.recyclerViewManage.adapter = adapter

        mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
            }
        })

        binding.lifecycleOwner = this

        mImgMsgViewModel.navigateToUpdateFrag.observe(viewLifecycleOwner, Observer { imgMsg ->
            imgMsg?.let {
                this.findNavController().navigate(
                    ManageGridFragmentDirections.actionManageGridToUpdateFragment(imgMsg)
                )
                mImgMsgViewModel.onUpdateFragNavigated()
            }
        })

        // Grid setup
        val manager = GridLayoutManager(activity, 3)
        binding.recyclerViewManage.layoutManager = manager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener on close btn to navigate back to home fragment
        navController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_global_homeFragment)
        }

        // Instantiate lateinit vars for multi-select
        toolbarHeaderTxt = view.findViewById(R.id.update_fragment_header)
        deleteBtn = view.findViewById(R.id.delete_btn)

        // Click listener for delete btn
        deleteBtn.setOnClickListener {
            deleteImgMsg()
        }

    }

    private fun deleteImgMsg() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            for (imgMsg in selectedItems) {
                mImgMsgViewModel.deleteImgMsg(imgMsg)
            }

            mImgMsgViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.setData(it)
                }
            })

            isActionMode = false
            selectedItems.clear()
            adapter.selectedItems.clear()
            adapter.multiSelect = false
            counter = 0
            updateToolbarHeader(counter)
            Toast.makeText(context, "Successfully removed.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        if (selectedItems.size == 1) {
            builder.setMessage("Are you sure you want to delete this item?")
        } else {
            builder.setMessage("Are you sure you want to delete these items?")
        }
        builder.create().show()
    }

    fun startSelection(imgMsg: ImageMessage) {
        if (!isActionMode) {
            isActionMode = true
            selectedItems.add(imgMsg)
            counter++
            updateToolbarHeader(counter)
            deleteBtn.visibility = View.VISIBLE
        }
    }

    fun manageSelection(imgMsg: ImageMessage) {
        if (selectedItems.contains(imgMsg)) {
            selectedItems.remove(imgMsg)
            counter--
            updateToolbarHeader(counter)

            if (selectedItems.size == 0) {
                isActionMode = false
            }

        } else {
            selectedItems.add(imgMsg)
            counter++
            updateToolbarHeader(counter)
        }
    }

    private fun updateToolbarHeader(counter: Int) {
        if (counter == 0) {
            toolbarHeaderTxt.text = "Manage"
            deleteBtn.visibility = View.GONE
        } else if (counter == 1) {
            toolbarHeaderTxt.text = "1 item selected"
        } else {
            toolbarHeaderTxt.text = counter.toString() + " items selected"
        }
    }

}