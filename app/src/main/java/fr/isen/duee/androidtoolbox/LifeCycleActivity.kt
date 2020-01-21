package fr.isen.duee.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_life_cycle.*

private const val NUM_PAGES = 2

class LifeCycleActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        val fragment1 = LifeCycleFragment()
        val fragment2 = LifeCycleSecondFragment()

        mPager = findViewById(R.id.activity_lifecycle_viewpager)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter

        supportFragmentManager.beginTransaction().add(R.id.activity_lifecycle_layout, fragment1).commit()

        activity_lifecycle_button.setOnClickListener {
            if(fragment1.isVisible) {
                supportFragmentManager.beginTransaction().replace(R.id.activity_lifecycle_layout, fragment2).commit()
            }
            else {
                supportFragmentManager.beginTransaction().replace(R.id.activity_lifecycle_layout, fragment1).commit()
            }
        }

        notification("onCreate", true)
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return LifeCycleFragment()
                else -> return LifeCycleSecondFragment ()
            }
        }
    }

    private fun notification(message : String, IsActive : Boolean) {
        if (IsActive) {
            activity_lifecycle_text.text = message
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
        notification("onPause", false)
    }

    override fun onStop() {
        super.onStop()
        notification("onStop", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification("onDestroy", false)
        Toast.makeText(this@LifeCycleActivity, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}
