package fr.isen.duee.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        val sharedEmailValue = sharedPreferences.getString("email","defaultemail")
        val sharedPasswordValue = sharedPreferences.getString("password","defaultpassword")

        if(sharedEmailValue.equals("defaultemail") && sharedPasswordValue.equals("defaultpassword")){
            activity_login_email_input.setText("")
            activity_login_password_input.setText("")

        }else{
            if(sharedEmailValue.equals("admin") && sharedPasswordValue.equals("123")) {

                val message = "$sharedEmailValue est connecté"
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                this.finish()

                val homeActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(homeActivityIntent)
            }
        }


        activity_login_button.setOnClickListener {

            var identifiant = activity_login_email_input.text.toString()
            var password = activity_login_password_input.text.toString()

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
                    Toast.makeText(this@LoginActivity, "Mot de passe incorect", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@LoginActivity, "Identifiant incorect", Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_login_inscription_button.setOnClickListener {
            val inscriptionActivityIntent = Intent(this, InscriptionActivity::class.java)
            startActivity(inscriptionActivityIntent);
        }
    }
}
