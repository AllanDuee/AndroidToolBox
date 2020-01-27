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

        lifecycleButtonClick()
        saveButtonClick()
        permissionButtonClick()

        disconnectButtonClick()
    }

    fun lifecycleButtonClick(){
        homeLifecyclePicture.setOnClickListener {

            val intent = Intent(this, LifeCycleActivity::class.java)
            startActivity(intent);
        }
    }

    fun saveButtonClick(){
        homeSavePicture.setOnClickListener {

            val intent = Intent(this, SaveDataActivity::class.java)
            startActivity(intent);
        }
    }

    fun permissionButtonClick(){
        homePermissionsPicture.setOnClickListener {

            val intent = Intent(this, PhoneInformationActivity::class.java)
            startActivity(intent);
        }
    }

    fun disconnectButtonClick(){
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        homeDisconnectButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.remove("email")
            editor.remove("password")
            editor.commit()

            this.finish()

            val loginActivityIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivityIntent)
        }
    }
}
