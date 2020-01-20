package fr.isen.duee.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        activity_home_lifecycle_picture.setOnClickListener {

            val intent = Intent(this, LifeCycleActivity::class.java)
            startActivity(intent);
        }
    }
}
