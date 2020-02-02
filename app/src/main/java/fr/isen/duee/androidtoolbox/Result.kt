package fr.isen.duee.androidtoolbox

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("name")
    @Expose
    var name: Name? = null
    @SerializedName("location")
    @Expose
    var location: Location? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("picture")
    @Expose
    var picture: Picture? = null

}
