package fr.isen.duee.androidtoolbox

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_phone_information.*

class PhoneInformationActivity : AppCompatActivity() {

    companion object {
        private val IMAGE_PICK_CODE_GALLERY = 1000;
        private val PERMISSION_CODE_GALLERY = 1001;
        private val IMAGE_CAPTURE_CODE_CAMERA = 1002;
        private val PERMISSION_CODE_CAMERA = 1003;
    }
    var image_uri: Uri? = null

    val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_information)

        imageClick()

        addContacts()

        contactRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PhoneInformationActivity)
            adapter = UsersAdapter(users, { user -> userItemClicked(user)})
        }

    }

    /*fun getContact() {
        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            val firstname = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            users.add()

        }
        phones.close()
    }*/

    fun userItemClicked(user: User) {
        Toast.makeText(this, "Clicked: ${user.firstName} ${user.lastName}", Toast.LENGTH_SHORT).show()

        //val bundle = Bundle()
        val userFirstname = user.firstName
        val userLastname = user.lastName
        val userEmail = user.email
        val userNumber = user.number
        val userBirthday = user.dateOfBirth

        /*bundle.putString("userFirstname", userFirstname)
        bundle.putString("userLastname", userLastname)
        bundle.putString("userEmail", userEmail)
        bundle.putString("userNumber", userNumber)
        bundle.putString("userBirthday", userBirthday)

        val fragment = SpecificUserFragment()
        fragment.arguments = bundle

        getSupportFragmentManager().beginTransaction().add(R.id.activity_phone_information, fragment).commit()*/

        val intent = Intent(this@PhoneInformationActivity,SpecificUserActivity::class.java)
        intent.putExtra("userFirstname",userFirstname)
        intent.putExtra("userLastname",userLastname)
        intent.putExtra("userEmail",userEmail)
        intent.putExtra("userNumber",userNumber)
        intent.putExtra("userBirthday",userBirthday)
        startActivity(intent)
    }

    fun addContacts() {
        users.add(User("DUEE", "Allan", "18/06/1997", "1245697854", "allan.duee@isen.yncrea.fr"))
        users.add(User("THOMAS", "Valentin", "12/01/1989", "0125469875", "valentin.thomas@isen.yncrea.fr"))
        users.add(User("DOE", "John", "05/01/1935", "0569874532", "john.doe@gmail.com"))
        users.add(User("BENNET", "Phil", "25/09/2011", "04563289", "phil.bennet@gmail.com"))
        users.add(User("BRIDGE", "Bob", "12/01/1989", "0125469875", "bob.bridge@gmail.com"))
        users.add(User("TODAY", "Tom", "16/08/1977", "0985632145", "tom.today@gmail.com"))
    }

    fun imageClick() {
        phoneInformationPicture.setOnClickListener {
            popUp()
        }
    }

    fun checkAndOpenGallery() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE_GALLERY);
            }
            else{
                //permission already granted
                pickImageFromGallery();
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    fun checkAndOpenCamera() {
        //if system os is Marshmallow or Above, we need to request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE_CAMERA)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE_CAMERA)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE_GALLERY)
    }

    fun popUp() {
        val builder = AlertDialog.Builder(this@PhoneInformationActivity)

        builder.setTitle("Veuillez séléctionner un mode :")
        builder.setNegativeButton("Gallery"){_,_ ->
            Toast.makeText(applicationContext,"You have choose the Gallery",Toast.LENGTH_SHORT).show()
            checkAndOpenGallery()
        }
        builder.setPositiveButton("Camera"){_,_ ->
            Toast.makeText(applicationContext,"You have choose the Camera",Toast.LENGTH_SHORT).show()
            checkAndOpenCamera()
        }
        builder.setNeutralButton("Cancel"){_,_ ->
            Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE_GALLERY -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE_CAMERA -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    openCamera()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE_GALLERY){
            phoneInformationPicture.setImageURI(data?.data)
        }
        else  if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            phoneInformationPicture.setImageURI(image_uri)
        }
    }
}
