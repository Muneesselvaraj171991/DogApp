package com.dog.app.view

import androidx.navigation.Navigation.findNavController
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dog.app.R


class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById(R.id.imageView_dog) as ImageView
        loadGif(imageView)
        val btnStart = view.findViewById(R.id.btn_Start) as Button
        btnStart.setOnClickListener {
            findNavController(view).navigate(R.id.dogListFragment)
        }
    }

    private fun loadGif(view: ImageView) {
        Glide.with(this)
                .load(R.raw.dog)
            .into(view)
    }
}