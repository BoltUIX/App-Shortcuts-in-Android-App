package com.boltuix.appshortcut

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.boltuix.appshortcut.databinding.FragmentAppShortcutBinding
import com.boltuix.appshortcut.databinding.FragmentDetailBinding
import java.lang.Exception


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.textId.text= "Z
        binding.apply {

            textId.text=""

            textId2.text=""

            textId2.setOnClickListener {
                Log.d("ta1","")
                Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
            }

        }

        binding.textId.apply {
            text=""
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}