package fr.isen.duee.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_inscription.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson
import android.widget.Toast
import java.io.File
import java.io.FileReader

class InscriptionActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()

    fun getAgeUser(dateOfBirth: String): Long{
        val dateFormat = SimpleDateFormat("M/dd/yyyy")
        val currentDate = Calendar.getInstance().timeInMillis

        val userDateOfBirth = dateFormat.parse(dateOfBirth).time

        val ageInMillis = currentDate - userDateOfBirth
        val ageInSecond = ageInMillis / 1000
        val ageInMinutes = ageInSecond / 60
        val ageInHours = ageInMinutes / 60
        val ageInDays = ageInHours / 24
        val ageInYears = ageInDays / 365

        return ageInYears
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        var textview_date = this.activity_inscription_date_text

        textview_date!!.text = "--/--/----"

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        textview_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@InscriptionActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        activity_inscription_button.setOnClickListener {

            val gson = Gson()

            val lastName = activity_inscription_lastname_input_text.text.toString()
            val firstName = activity_inscription_firstname_input_text.text.toString()
            val dateOfBirth = activity_inscription_date_text.text.toString()


            val user = User( lastName, firstName, dateOfBirth )
            File(cacheDir.absolutePath + "userInfo.json").writeText(gson.toJson(user))
            Toast.makeText(this@InscriptionActivity, "L'utilisateur a été ajouté", Toast.LENGTH_SHORT).show()
        }

        activity_inscription_read_button.setOnClickListener {

            val gson = Gson()

            var user = User( "", "", "" )

            user = gson.fromJson(FileReader(cacheDir.absolutePath + "userInfo.json"), User::class.java)

            val firstNameUser = user.firstName
            val lastNameUser = user.lastName
            val dateOfBirthUser = user.dateOfBirth

            val ageUser = getAgeUser(user.dateOfBirth)

            val builder = AlertDialog.Builder(this@InscriptionActivity)

            builder.setTitle("Informations du Json")

            builder.setMessage("Voici les informations du Json : \n\nNom: $lastNameUser\nPrénom: $firstNameUser\nDate de Naissance: $dateOfBirthUser\n\nAge : $ageUser ans")

            builder.setNegativeButton("Cancel"){_,_ ->
                Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        }
    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        activity_inscription_date_text!!.text = sdf.format(cal.getTime())
    }
}
