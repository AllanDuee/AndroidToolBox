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
    private val fragment1 = LifeCycleFragment()
    private val fragment2 = LifeCycleSecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        sliderBetweenFragment()
        changeFrangmentButtonClick()

        notification(getString(R.string.on_create), true)
    }

    fun sliderBetweenFragment() {
        mPager = findViewById(R.id.lifecycleViewPager)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter

        supportFragmentManager.beginTransaction().add(R.id.lifecycleLayout, fragment1).commit()
    }

    fun changeFrangmentButtonClick() {
        lifeCycleButton.setOnClickListener {
            if(fragment1.isVisible) {
                supportFragmentManager.beginTransaction().replace(R.id.lifecycleLayout, fragment2).commit()
            }
            else {
                supportFragmentManager.beginTransaction().replace(R.id.lifecycleLayout, fragment1).commit()
            }
        }
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            this.finish()
            //mPager.currentItem = mPager.currentItem - 1
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
            lifecycleTitle.text = message
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
        notification(getString(R.string.on_pause), false)
    }

    override fun onStop() {
        super.onStop()
        notification(getString(R.string.on_stop), false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification(getString(R.string.on_destroy), false)
        Toast.makeText(this@LifeCycleActivity, getString(R.string.on_destroy), Toast.LENGTH_SHORT).show()
    }
}
