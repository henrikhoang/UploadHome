package com.example.uploadhome

import android.net.Uri

data class HouseInfo(var name : String? = "",
                     var email : String? = "",
                     var address : String? = "",
                     var pictures : ArrayList<Uri>? = ArrayList(),
                     var note : String? = "") {

}