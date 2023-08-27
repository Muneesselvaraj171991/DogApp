package com.dog.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dog.app.R
import com.dog.app.databinding.FragmentDetailBinding
import com.dog.app.model.Dog

class DetailFragment : Fragment() {
    private var mDog : Dog? = null
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val dataBinding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater, R.layout.fragment_detail, container, false)
        dataBinding.fragment=this
        dataBinding.dog = args.dog
        mDog = dataBinding.dog
        return dataBinding.root
    }


}