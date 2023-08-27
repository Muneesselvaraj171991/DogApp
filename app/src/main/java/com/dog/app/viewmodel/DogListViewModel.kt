package com.dog.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dog.app.model.Dog
import com.dog.app.network.RemoteCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DogListViewModel : ViewModel() {
    private val mRemoteCall: RemoteCall = RemoteCall.remoteCallInstance
    private val mDogListLivedata = MutableLiveData<List<Dog>>()
    private var mIsSomethingWentWrong: Boolean = false

    fun init() {
        viewModelScope.async(Dispatchers.IO) {
            mRemoteCall.getDogNameList(){result: Boolean ->

                if(result) {
                    fetDogDetails(0)
                }
            }

        }
    }

      fun fetDogDetails(index: Int) {
         viewModelScope.async(Dispatchers.IO) {
              fetchData(index)

         }
     }
    private suspend fun fetchData(index: Int) {

            mRemoteCall.getDogList(object : RemoteCall.Result {
                override fun onResponse(dogList: List<Dog>) {
                    mDogListLivedata.postValue(dogList)
                }
                override fun onFailure() {
                    mDogListLivedata.postValue(emptyList())
                    mIsSomethingWentWrong = true
                }

                override fun onFetchedAllData() {
                    mDogListLivedata.postValue(emptyList())
                    mIsSomethingWentWrong = false
                }

            }, index)
        }

    fun  IsError(): Boolean {
        return mIsSomethingWentWrong
    }

    val dogListLiveData: LiveData<List<Dog>>
        get() = mDogListLivedata
}