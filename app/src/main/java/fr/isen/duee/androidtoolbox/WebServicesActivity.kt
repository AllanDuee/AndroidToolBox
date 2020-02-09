package fr.isen.duee.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.duee.androidtoolbox.user.UserList
import fr.isen.duee.androidtoolbox.user.UserListAdapter
import kotlinx.android.synthetic.main.activity_web_services_actibity.*

class WebServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services_actibity)

        val gson = GsonBuilder().create()
        var request: StringRequest
        val url = "https://randomuser.me/api/?inc=name,location,email,picture&results=10"

        request = StringRequest(Request.Method.GET, url, Response.Listener <String>{
            val userList = gson.fromJson(it, UserList::class.java)

           webServicesRecyclerView.layoutManager = LinearLayoutManager(this)
           webServicesRecyclerView.adapter =
               UserListAdapter(userList)
        }, Response.ErrorListener {
            Toast.makeText(this, getString(R.string.error_api), Toast.LENGTH_LONG).show()
        })
        Volley.newRequestQueue(this).add(request)

    }
}
