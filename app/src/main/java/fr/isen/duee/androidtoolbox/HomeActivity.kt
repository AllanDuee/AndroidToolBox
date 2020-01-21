package fr.isen.duee.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        activity_home_lifecycle_picture.setOnClickListener {

            val intent = Intent(this, LifeCycleActivity::class.java)
            startActivity(intent);
        }

        activity_home_disconnect_button.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.remove("email")
            editor.remove("password")
            editor.commit()

            this.finish()

            val loginActivityIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivityIntent)
        }

        activity_home_save_picture.setOnClickListener {

            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent);
        }
    }
}
