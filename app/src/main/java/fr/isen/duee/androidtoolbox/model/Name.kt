package fr.isen.duee.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Name {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("first")
    @Expose
    var first: String? = null
    @SerializedName("last")
    @Expose
    var last: String? = null

}
