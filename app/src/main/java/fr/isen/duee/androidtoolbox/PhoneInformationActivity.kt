package fr.isen.duee.androidtoolbox

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_phone_information.*

class PhoneInformationActivity : AppCompatActivity(), LocationListener {
    override fun onLocationChanged(location: Location?) {
        phoneInformationLatLongValue.text = getString(R.string.phone_information_long_lat_permission,  location?.latitude, location?.longitude)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onStop() {
        super.onStop()
        locationManager?.removeUpdates(this)
    }

    companion object {
        private val IMAGE_PICK_CODE_GALLERY = 1000
        private val PERMISSION_CODE_GALLERY = 1001
        private val IMAGE_CAPTURE_CODE_CAMERA = 1002
        private val PERMISSION_CODE_CAMERA = 1003
        private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
        private val PERMISSIONS_REQUEST_FINE_LOCATION = 101
        private val PERMISSIONS_REQUEST_COARSE_LOCATION = 102
    }
    var image_uri: Uri? = null

    val contacts = mutableListOf<Contact>()

    private var locationManager : LocationManager? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_information)

        imageClick()

        loadContacts()

        addContacts()

        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        contactRecyclerView.adapter = ContactAdapter(contacts, { contact -> contactItemClicked(contact)})

    }

    @SuppressLint("MissingPermission")
    fun  getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)

        val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            phoneInformationLatLongValue.text = getString(R.string.phone_information_long_lat_permission,  location?.latitude, location?.longitude)
        }
    }

    fun loadLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONS_REQUEST_FINE_LOCATION)
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        PERMISSIONS_REQUEST_COARSE_LOCATION)
                    //callback onRequestPermissionsResult
                } else {
                    getLocation()
                }
        else {
            getLocation()
        }
    }

    fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
                loadLocation()
            } else {
                getContacts()
                loadLocation()
            }
        }  else {
            getContacts()
            loadLocation()
        }
    }

    private fun getContacts(): StringBuilder {
        val builder = StringBuilder()
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)
        cursor?.let {
            if (it.count > 0) {
                while (it.moveToNext()) {
                    val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (it.getString(
                        it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)
                        cursorPhone?.let {
                            if(it.count > 0) {
                                while (it.moveToNext()) {
                                    val phoneNumValue = it.getString(
                                        it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                        phoneNumValue).append("\n\n")
                                    contacts.add(Contact(name,phoneNumValue))
                                }
                            }
                            it.close()
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.no_contact_available), Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }
        return builder
    }

    fun contactItemClicked(contact: Contact) {
        Toast.makeText(this, "Clicked: ${contact.name}", Toast.LENGTH_SHORT).show()

        //val bundle = Bundle()
        val contactName = contact.name
        val contactNumber = contact.number

        val intent = Intent(this@PhoneInformationActivity,SpecificUserActivity::class.java)
        intent.putExtra("contactName",contactName)
        intent.putExtra("contactNumber",contactNumber)
        startActivity(intent)
    }

    fun addContacts() {
        contacts.add(Contact("Allan DUEE", "1245697854"))
        contacts.add(Contact("Valentin THOMAS", "0125469875"))
        contacts.add(Contact("John DOE", "0569874532"))
        contacts.add(Contact("Phil BENNET", "04563289"))
        contacts.add(Contact("Bob BRIDGE", "0463259841"))
        contacts.add(Contact("Tom Today", "0985632145"))
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

        builder.setTitle(getString(R.string.phone_information_activity_popup_title))
        builder.setNegativeButton("Gallery"){_,_ ->
            Toast.makeText(applicationContext,getString(R.string.phone_information_activity_popup_gallery),Toast.LENGTH_SHORT).show()
            checkAndOpenGallery()
        }
        builder.setPositiveButton("Camera"){_,_ ->
            Toast.makeText(applicationContext,getString(R.string.phone_information_activity_popup_camera),Toast.LENGTH_SHORT).show()
            checkAndOpenCamera()
        }
        builder.setNeutralButton("Cancel"){_,_ ->
            Toast.makeText(applicationContext,getString(R.string.phone_information_activity_popup_cancel),Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        loadContacts()
                    } else {
                        Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            PERMISSIONS_REQUEST_FINE_LOCATION -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation()
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSIONS_REQUEST_COARSE_LOCATION -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation()
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
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
