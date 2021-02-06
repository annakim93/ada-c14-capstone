package com.example.adacapstone.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodel.ImgMsgViewModel

class UpdateFragment : Fragment() {

    // Room database
    private lateinit var mImgMsgViewModel: ImgMsgViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        container?.removeAllViews()

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        view.findViewById<ImageView>(R.id.selected_update_img).setImageBitmap(args.currentImgMsg.image)
        view.findViewById<EditText>(R.id.update_alert_text).setText(args.currentImgMsg.msg)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Model
        mImgMsgViewModel = ViewModelProvider(this).get(ImgMsgViewModel::class.java)

        // Click listener on close btn to navigate back to home fragment
        val navController: NavController = Navigation.findNavController(view)
        val closeBtn: ImageView = view.findViewById(R.id.close_update_img_btn)

        closeBtn.setOnClickListener {
            navController.navigate(R.id.action_updateFragment_to_manageGrid)
        }

        // Click listener for update / save changes button
        val saveBtn: ImageView = view.findViewById(R.id.save_btn)

        saveBtn.setOnClickListener {
            val updatedMsg = view.findViewById<EditText>(R.id.update_alert_text).text.toString()
            val updatedImg = view.findViewById<ImageView>(R.id.selected_update_img)
            val updatedBM = (updatedImg.drawable as BitmapDrawable).bitmap

            if (inputCheck(updatedMsg)) {
                val updatedImgMsg = ImageMessage(args.currentImgMsg.id, updatedMsg, updatedBM) // Create imgMsg object
                mImgMsgViewModel.updateImgMsg(updatedImgMsg)
                navController.navigate(R.id.action_updateFragment_to_manageGrid)
                Toast.makeText(requireContext(), "Successfully updated.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please make sure all fields are complete.", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun inputCheck(message: String): Boolean {
        return !(TextUtils.isEmpty(message))
    }

}