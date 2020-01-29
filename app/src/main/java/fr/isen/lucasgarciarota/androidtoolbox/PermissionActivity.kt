package fr.isen.lucasgarciarota.androidtoolbox

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_permission.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PermissionActivity : AppCompatActivity() {

    companion object{
        //Permission code
        private const val IMAGE_PICK_REQUEST = 1000
        private const val CAMERA_PICK_REQUEST = 4444
        private const val CONTACT_PICK_REQUEST = 1001

        const val PERMISSION_CODE = 1002
    }

    lateinit var mFusedLocationClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val users: ArrayList<String> = ArrayList()
        recycleView.layoutManager= LinearLayoutManager(this)
        recycleView.adapter = UsersAdapter(users)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        pickContact()

        photoButton.setOnClickListener {
            withItems()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        findViewById<TextView>(R.id.lat).text = location.latitude.toString()
                        findViewById<TextView>(R.id.longi).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            findViewById<TextView>(R.id.lat).text = mLastLocation.latitude.toString()
            findViewById<TextView>(R.id.longi).text = mLastLocation.longitude.toString()
        }
    }


    fun imageFromGallery(){
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, IMAGE_PICK_REQUEST)
    }

    fun imageFromCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_PICK_REQUEST)
            }
        }
    }

    fun withItems() {

        val items = arrayOf("Prendre une photo", "Galerie")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choisir : ")
            setItems(items) { _, which ->
                if(items[which] == "Prendre une photo"){
                    imageFromCamera()
                }
                else{
                    imageFromGallery()
                }
            }

            setPositiveButton("Retour", backButtonClick)
            show()
        }
    }

    private val backButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, "Aucune photo choisie", Toast.LENGTH_SHORT).show()
    }


    private fun pickContact() {
        val pickContactIntent = Intent(Intent.ACTION_PICK).apply {
            // Show user only the contacts that include phone numbers.
            setDataAndType(
                Uri.parse("content://contacts"),
                ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            )
        }

        startActivityForResult(pickContactIntent, CONTACT_PICK_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_REQUEST){
            photoButton.setImageURI(data?.data)
        }

        else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PICK_REQUEST){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoButton.setImageBitmap(imageBitmap)
        }
        else if (requestCode == CONTACT_PICK_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                val projection: Array<String> =
                    arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                // Get the URI that points to the selected contact
                data?.data.also { contactUri ->
                    contentResolver.query(contactUri, projection, null, null, null)?.apply {
                        moveToFirst()
                        val column: Int =
                            getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        getString(column)

                    }
                }
            }
        }
    }
}
