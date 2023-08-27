package com.dog.app.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dog.app.R
import com.dog.app.model.Dog
import com.dog.app.network.CheckInternetConnection
import com.dog.app.view.adapter.DogListAdapter
import com.dog.app.viewmodel.DogListViewModel
import com.google.android.material.snackbar.Snackbar

class DogListFragment : Fragment() {
    private var isLoading: Boolean = false
    lateinit var gridlayoutManager : GridLayoutManager
    private var visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as Context
        val networkConnection = CheckInternetConnection(activity)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular)
        val footerProgressBar = view.findViewById<ProgressBar>(R.id.footer_loading)
        val model = ViewModelProvider(this).get(DogListViewModel::class.java)
        val textviewCheckConnection = view.findViewById<TextView>(R.id.textview_checkConnection)
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                textviewCheckConnection.visibility = View.GONE
                model.init()
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                val adapter =
                    DogListAdapter( object : DogListAdapter.ItemClick {
                        override fun onClick(dog: Dog) {
                            val dogDetails =DogListFragmentDirections.actionDogListFragmentToDetailFragment(dog)
                             findNavController(view).navigate(dogDetails)
                         }
                    })
                recyclerView.adapter = adapter
                model.dogListLiveData.observe(viewLifecycleOwner) { dogList: List<Dog> ->
                      if(dogList.isNotEmpty()) {
                          if (isLoading) {
                              isLoading = false
                          }
                          adapter.submitList(dogList)
                      } else {
                          val snack =
                              Snackbar.make(view, if(model.IsError()) R.string.unable_to_fetch_list else  R.string.feed_over, Snackbar.LENGTH_LONG)
                          snack.show()

                      }
                    progressBar.visibility = View.GONE
                    footerProgressBar.visibility =   View.GONE

                }


                 gridlayoutManager = GridLayoutManager(
                    context, 2, LinearLayoutManager.VERTICAL, false)
                 recyclerView.layoutManager = gridlayoutManager
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        totalItemCount = gridlayoutManager.itemCount

                        lastVisibleItem = gridlayoutManager.findLastCompletelyVisibleItemPosition()

                        if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                        {
                            isLoading = true
                            footerProgressBar.visibility =   View.VISIBLE

                            model.fetDogDetails(totalItemCount-1)


                        }
                    }
                })

            } else {
                progressBar.visibility = View.GONE
                textviewCheckConnection.visibility = View.VISIBLE
            }

        }

    }
}