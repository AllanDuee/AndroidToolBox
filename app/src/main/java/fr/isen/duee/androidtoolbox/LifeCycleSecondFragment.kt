package fr.isen.duee.androidtoolbox


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_life_cycle_second.*

class LifeCycleSecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_life_cycle_second, container, false)
    }

    private fun notification(message : String, IsActive : Boolean) {
        if (IsActive) {
            secondFragmentLifecycleText.text = message
        }
        else {
            Log.d(getString(R.string.tag), message)
        }
    }

    override fun onStart() {
        super.onStart()
        notification(getString(R.string.on_start), true)
    }

    override fun onResume() {
        super.onResume()
        notification(getString(R.string.on_resume), true)
    }

    override fun onPause() {
        super.onPause()
        notification(getString(R.string.on_pause), true)
    }

    override fun onStop() {
        super.onStop()
        notification(getString(R.string.on_stop), false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification(getString(R.string.on_destroy), false)
    }
}
