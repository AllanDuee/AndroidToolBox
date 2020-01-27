package fr.isen.duee.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_specific_user.*

class SpecificUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_user)

        changeActivityTextView()
        buttonGoBackClick()
    }

    fun changeActivityTextView() {
        val userFirstname=intent.getStringExtra("userFirstname")
        val userLarstname = intent.getStringExtra("userLastname")
        val userEmail = intent.getStringExtra("userEmail")
        val userPhoneNumber = intent.getStringExtra("userNumber")
        val userBirthday = intent.getStringExtra("userBirthday")

        specificUserFirstnameValue.text = userFirstname
        specificUserLastnameValue.text = userLarstname
        specificUserEmailValue.text = userEmail
        specificUserPhoneNumberValue.text = userPhoneNumber
        specificUserDateOfBirthValue.text = userBirthday
    }

    fun buttonGoBackClick() {
        specificUserGoBackButton.setOnClickListener {
           this.finish()

            val phoneInformationActivityIntent = Intent(applicationContext, PhoneInformationActivity::class.java)
            startActivity(phoneInformationActivityIntent)
        }
    }
}
