package fr.isen.duee.androidtoolbox


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_specific_user.*

class SpecificUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specific_user, container, false)
    }

    override fun onResume() {
        super.onResume()
        changeFragmentTextView()
        buttonGoBackClick()
    }

    fun changeFragmentTextView() {
        val userFirstname = this.arguments!!.getString("userFirstname")!!.toString()
        val userLarstname = this.arguments!!.getString("userLastname")!!.toString()
        val userEmail = this.arguments!!.getString("userEmail")!!.toString()
        val userPhoneNumber = this.arguments!!.getString("userNumber")!!.toString()
        val userBirthday = this.arguments!!.getString("userBirthday")!!.toString()

        fragment_specific_user_firstname_value.text = userFirstname
        fragment_specific_user_lastname_value.text = userLarstname
        fragment_specific_user_email_value.text = userEmail
        fragment_specific_user_phone_number_value.text = userPhoneNumber
        fragment_specific_user_birthday_value.text = userBirthday
    }

    fun buttonGoBackClick() {
        fragment_specific_user_button.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.remove(this)?.commitAllowingStateLoss();
        }
    }
}
