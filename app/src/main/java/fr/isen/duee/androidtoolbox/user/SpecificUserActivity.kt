package fr.isen.duee.androidtoolbox.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.duee.androidtoolbox.PhoneInformationActivity
import fr.isen.duee.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_specific_user.*

class SpecificUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_user)

        changeActivityTextView()
        buttonGoBackClick()
    }

    fun changeActivityTextView() {
        val contactName=intent.getStringExtra("contactName")
        val contactNumber = intent.getStringExtra("contactNumber")

        specificUserNameValue.text = contactName
        specificUserPhoneNumberValue.text = contactNumber
    }

    fun buttonGoBackClick() {
        specificUserGoBackButton.setOnClickListener {
           this.finish()

            val phoneInformationActivityIntent = Intent(applicationContext, PhoneInformationActivity::class.java)
            startActivity(phoneInformationActivityIntent)
        }
    }
}
