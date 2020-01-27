package fr.isen.duee.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val sharedPrefFile = "sharedPrefFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkPreferences()
        loginButtonClick()
    }

    fun checkPreferences() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedEmailValue = sharedPreferences.getString("email","defaultemail")
        val sharedPasswordValue = sharedPreferences.getString("password","defaultpassword")

        if(sharedEmailValue.equals("defaultemail") && sharedPasswordValue.equals("defaultpassword")){
            loginEmailInputText.setText("")
            loginPasswordInputText.setText("")

        }else{
            if(sharedEmailValue.equals("admin") && sharedPasswordValue.equals("123")) {

                val message = "$sharedEmailValue est connecté"
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                this.finish()

                val homeActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(homeActivityIntent)
            }
        }
    }
    fun loginButtonClick() {
        loginValidateButton.setOnClickListener {

            val editor:SharedPreferences.Editor =  this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE).edit()

            var identifiant = loginEmailInputText.text.toString()
            var password = loginPasswordInputText.text.toString()

            editor.putString("email",identifiant)
            editor.putString("password",password)
            editor.apply()
            editor.commit()

            if(identifiant.equals("admin") && password.equals("123")) {

                val message = "$identifiant est connecté"
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                this.finish()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent);
            }
            else {
                if(identifiant.equals("admin")) {
                    Toast.makeText(this@LoginActivity, getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@LoginActivity, getString(R.string.incorrect_pseudo), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
