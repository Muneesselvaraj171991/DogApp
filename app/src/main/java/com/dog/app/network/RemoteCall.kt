package com.dog.app.network

import com.dog.app.model.Dog
import com.dog.app.model.DogNameList
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class RemoteCall private constructor() {
    private  var mDogNameList: List<String>? = null
    private var mDogList: ArrayList<Dog> = ArrayList()

    fun getDogNameList(myCallback: (result: Boolean) -> Unit) {
        if (mDogNameList==null) {
            val url = URL("$URL/breeds/list/all")
            try {
                val urlConnection = url.openConnection() as HttpURLConnection
                val data = urlConnection.inputStream.bufferedReader().readText()
                mDogNameList = DogNameList(JSONObject(data)).dogList
                myCallback.invoke(true)

            } catch (e: Exception) {
                myCallback.invoke(false)
                e.printStackTrace()
            }
        } else {
            //set callback to true if mDogNameList is not null, as you know Remote call I kept it as Singleton.
            myCallback.invoke(true)
        }

    }

    private fun findValidIndex(index: Int): Int {
        var indexWithThreshold = index +20// threshold
        if(indexWithThreshold <= mDogNameList!!.size) {
            return indexWithThreshold
        }
        indexWithThreshold = mDogNameList!!.size - index
        return indexWithThreshold+index
    }

    fun getDogList(result: Result, index: Int) {
        var startIndex = index

        val endIndex = findValidIndex(index)
        if(endIndex < mDogNameList!!.size) {
            //Return cached list if requested "endIndex" is less than mdoglist to avoid frequent api call.
            if(endIndex>mDogList.size) {
            for (pos in startIndex until endIndex) {
                val dogName = mDogNameList!![pos]
                val imageUrl =  "$URL/breed/$dogName/images/random"

                try {
                    val urlConnection = URL(imageUrl).openConnection() as HttpURLConnection
                    val data = urlConnection.inputStream.bufferedReader().readText()
                    val dogList = JSONObject(data)
                    val findSubBreed =dogName.split("/").toTypedArray()
                    if(findSubBreed.size == 2) {
                        mDogList.add(Dog(dogName, findSubBreed[1], dogList.getString("message")))

                    } else {
                        mDogList.add(Dog(dogName, "NA", dogList.getString("message")))

                    }
                } catch (e: Exception) {
                    result.onFailure()
                    e.printStackTrace()
                }
            }
            }
            result.onResponse(mDogList)
        } else {
            result.onFetchedAllData()
        }
    }
    interface Result {
        fun onResponse(dogList: List<Dog>)
        fun onFailure()
        fun onFetchedAllData()
    }
    companion object {
        private const val URL = "https://dog.ceo/api"
        private var sRemoteCall: RemoteCall? = null
        @get:Synchronized
        val remoteCallInstance: RemoteCall
            get() {
                if (sRemoteCall == null) {

                    sRemoteCall = RemoteCall()
                }
                return sRemoteCall!!
            }
    }
}