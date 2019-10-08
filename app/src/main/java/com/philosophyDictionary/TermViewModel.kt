package com.philosophyDictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TermViewModel : ViewModel(){

    private var fullTerms: List<Term> = CustomApplication.fullTerms

    private var liveData: MutableLiveData<List<Term>> = MutableLiveData()
    private var favouriteLiveData: MutableLiveData<List<Term>> = MutableLiveData()
    init {
        liveData.value = fullTerms
        LoadFavourite()
    }

    fun searchFilter(text: String) {

        var text = text

        if (text.isEmpty()) {
            resetFilter()
        } else {
            text = text.toLowerCase()
            val termsResult = ArrayList<Term>()
            for (item in CustomApplication.fullTerms) {
                if (item.Definition.toLowerCase().contains(text)) {
                    termsResult.add(item)
                }
            }
            liveData.value = termsResult
        }
    }

    fun isFavourite(item: Term):Boolean{
        return favouriteLiveData.value?.contains(item) ?: false
    }

    fun favouriteFilter() {
        liveData.value = favouriteLiveData.value
    }

    fun resetFilter(){
        liveData.value = fullTerms
    }

    fun getLiveData():LiveData<List<Term>>{
        return liveData
    }
    fun getFavouriteLiveData():LiveData<List<Term>>{
        return favouriteLiveData
    }

    fun switchFavourite(term: Term){
        CustomApplication.termDao.insertIfExistDelete(term)
        LoadFavourite()
    }

    fun deleteAll(){
        CustomApplication.termDao.deleteAll()
        LoadFavourite()
    }

    private fun LoadFavourite(){
        favouriteLiveData.value = CustomApplication.termDao.getAll()
    }
}