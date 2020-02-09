package fr.isen.duee.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_save_data.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson
import android.widget.Toast
import java.io.File
import java.io.FileReader

class SaveDataActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var encryptData = EncryptData()
    private val sharedPrefFile = "privateKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_data)

        dateButtonClick()
        saveUserInfoButton()
        readUserInfoButton()
    }

    fun dateButtonClick() {
        val textview_date = this.saveDataDatePickerZone

        textview_date?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@SaveDataActivity,
                    setCalendar(),
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
    }

    fun saveUserInfoButton() {
        saveDataSaveButton.setOnClickListener {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            encryptData.generateKeyPair(sharedPreferences)

            val lastName = encryptData.encrypt(saveDataLastnameInputText.text.toString(), sharedPreferences)
            val firstName =  encryptData.encrypt(saveDataFirstnameInputText.text.toString(),sharedPreferences)
            val dateOfBirth = encryptData.encrypt(saveDataDatePickerZone.text.toString(), sharedPreferences)

            val user = User( lastName, firstName, dateOfBirth )
           // val user = User(encryptData.encrypt(lastName,"lastName"),  encryptData.encrypt(firstName,"firstName"), encryptData.encrypt(dateOfBirth,"dateOfBirth") )
            File(cacheDir.absolutePath + "userInfo.json").writeText(Gson().toJson(user))

            Toast.makeText(this@SaveDataActivity, getString(R.string.user_saved), Toast.LENGTH_SHORT).show()
        }
    }

    fun readUserInfoButton() {
        saveDataReadButton.setOnClickListener {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
           var user = Gson().fromJson(FileReader(cacheDir.absolutePath + "userInfo.json"), User::class.java)

            val firstNameUser = encryptData.decrypt(user.firstName,sharedPreferences)
            val lastNameUser =encryptData.decrypt(user.lastName,sharedPreferences)
            val dateOfBirthUser = encryptData.decrypt(user.dateOfBirth,sharedPreferences)
            val ageUser = getUserAge(dateOfBirthUser)

            Toast.makeText(this@SaveDataActivity, getString(R.string.user_decrypt), Toast.LENGTH_SHORT).show()

            popUp(lastNameUser, firstNameUser, dateOfBirthUser, ageUser)
        }
    }

    fun popUp(lastName: String, firstName: String, dateOfBirth: String, ageUser: Long) {
        val builder = AlertDialog.Builder(this@SaveDataActivity)

        builder.setTitle(getString(R.string.json_info_title))
        builder.setMessage("Voici les informations du Json : \n\nNom: $lastName\nPrénom: $firstName\nDate de Naissance: $dateOfBirth\n\nAge : $ageUser ans")
        builder.setNegativeButton(getString(R.string.json_cancel_button)){_,_ ->
            Toast.makeText(applicationContext,getString(R.string.json_cancel_dialog),Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        saveDataDatePickerZone?.text = sdf.format(cal.getTime())
    }

    fun setCalendar () :  DatePickerDialog.OnDateSetListener {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        return dateSetListener
    }

    fun getUserAge(dateOfBirth: String): Long{
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
}
