package fr.isen.duee.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.w3c.dom.Text


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        activity_login_button.setOnClickListener {

            var identifiant = activity_login_email_input.text.toString()
            var password = activity_login_password_input.text.toString()

            if(identifiant.equals("admin") && password.equals("123")) {

                val message = "$identifiant est connecté"
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

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
    }
}
