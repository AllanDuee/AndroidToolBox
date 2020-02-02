package fr.isen.duee.androidtoolbox

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Info {

    @SerializedName("seed")
    @Expose
    var seed: String? = null
    @SerializedName("results")
    @Expose
    var results: String? = null
    @SerializedName("page")
    @Expose
    var page: String? = null
    @SerializedName("version")
    @Expose
    var version: String? = null

}
