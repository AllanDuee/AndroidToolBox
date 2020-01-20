package fr.isen.duee.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_life_cycle.*

class LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)
    }

    override fun onStart() {
        super.onStart()
        activity_lifecycle_text.text ="Activité lancé"
    }

    override fun onResume() {
        super.onResume()
        activity_lifecycle_text.text ="Activité résumé"
    }

    override fun onPause() {
        super.onPause()
        Log.d("INFO","Activité en pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("INFO","Activité stoppé")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this@LifeCycleActivity, "l'activité a été détruite", Toast.LENGTH_SHORT).show()
    }
}
