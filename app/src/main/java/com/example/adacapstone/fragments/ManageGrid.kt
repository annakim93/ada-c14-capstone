package com.example.adacapstone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel
import com.example.adacapstone.databinding.FragmentManageGridBinding
import com.example.adacapstone.utils.GridImageAdapter
import com.example.adacapstone.utils.ImgMsgListener

class ManageGrid : Fragment() {

    private lateinit var mImgMsgViewModel: ImgMsgViewModel
    private lateinit var adapter: GridImageAdapter

    // Action mode vars for mutliple selection
    var isActionMode = false
    private val selectedItems = arrayListOf<ImageMessage>()
    private var counter = 0
    private lateinit var toolbarHeaderTxt: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var deleteBtn: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        container?.removeAllViews()

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentManageGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_grid, container, false)

        val application = requireNotNull(this.activity).application

        // Get reference to ViewModel associated with manage grid fragment
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)
        binding.imgMsgViewModel = mImgMsgViewModel

        adapter = GridImageAdapter(ImgMsgListener { imgMsgId ->
//            Toast.makeText(context, "You clicked $imgMsgId", Toast.LENGTH_LONG).show()
            mImgMsgViewModel.onImgMsgClicked(imgMsgId)
        }, this)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener on close btn to navigate back to home fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_btn)

        closeBtn.setOnClickListener{
            navController.navigate(R.id.action_global_homeFragment)
        }

        toolbarHeaderTxt = view.findViewById(R.id.update_fragment_header)
        toolbar = view.findViewById(R.id.manage_frag_toolbar)
        deleteBtn = view.findViewById(R.id.delete_btn)
//        // Visibility of delete button
//        val deleteBtn: ImageView = view.findViewById(R.id.delete_btn)
//        if (adapter.selectedItems.size > 0) {
//            deleteBtn.visibility = View.VISIBLE
//        } else {
//            deleteBtn.visibility = View.GONE
//        }

    }

    fun startSelection(imgMsg: ImageMessage) {
        if (!isActionMode) {
            isActionMode = true
            selectedItems.add(imgMsg)
            counter++
            updateToolbarHeader(counter)
            deleteBtn.visibility = View.VISIBLE
//            toolbar.inflateMenu(R.menu.multi_select_menu)
        }
    }

    fun manageSelection(imgMsg: ImageMessage) {
        if (selectedItems.contains(imgMsg)) {
            selectedItems.remove(imgMsg)
            counter--
            updateToolbarHeader(counter)
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