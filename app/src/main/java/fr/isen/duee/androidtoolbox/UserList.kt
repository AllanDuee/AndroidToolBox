package fr.isen.duee.androidtoolbox

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserList {

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("info")
    @Expose
    var info: Info? = null

}
