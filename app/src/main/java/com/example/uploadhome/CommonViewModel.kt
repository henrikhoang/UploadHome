package com.example.uploadhome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {

    var navigationCommands : MutableLiveData<NavigationCenter>? = MutableLiveData()
    var houseInfo : HouseInfo = HouseInfo()

    fun navigate(destination: Int) {
        navigationCommands?.postValue(NavigationCenter.To(destination))
    }

    fun saveInfoStep1(inputName : String, inputEmail : String, inputAddress : String, inputNote : String?) {
        houseInfo.apply {
            name = inputName
            email = inputEmail
            address = inputAddress
            note = inputNote
        }
    }

}