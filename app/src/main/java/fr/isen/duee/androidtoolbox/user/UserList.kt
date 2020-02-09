package fr.isen.duee.androidtoolbox.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.isen.duee.androidtoolbox.model.Info
import fr.isen.duee.androidtoolbox.model.Result

class UserList {

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("info")
    @Expose
    var info: Info? = null

}
