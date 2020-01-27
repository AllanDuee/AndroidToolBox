package fr.isen.duee.androidtoolbox


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_life_cycle.*

class LifeCycleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_life_cycle, container, false)
    }

    private fun notification(message : String, IsActive : Boolean) {
        if (IsActive) {
            fragmentLifecycleText.text = message
        }
        else {
            Log.d("TAG", message)
        }
    }

    override fun onStart() {
        super.onStart()
        notification("onStart", true)
    }

    override fun onResume() {
        super.onResume()
        notification("onResume", true)
    }

    override fun onPause() {
        super.onPause()
        notification("onPause", true)
    }

    override fun onStop() {
        super.onStop()
        notification("onStop", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification("onDestroy", false)
    }
}
