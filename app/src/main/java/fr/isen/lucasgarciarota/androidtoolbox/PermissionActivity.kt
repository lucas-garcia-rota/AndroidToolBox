package fr.isen.lucasgarciarota.androidtoolbox

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.set
import kotlinx.android.synthetic.main.activity_permission.*
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PermissionActivity : AppCompatActivity() {

    companion object{
        //Permission code
        private const val IMAGE_PICK_REQUEST = 1000
        private const val CAMERA_PICK_REQUEST = 4444
        private const val CONTACT_PICK_REQUEST = 1001

        const val PERMISSION_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        photoButton.setOnClickListener {
            withItems()
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

        val items = arrayOf("Camera", "Galerie")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choisir : ")
            setItems(items) { _, which ->
                if(items[which] == "Camera"){
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_REQUEST){
            photoButton.setImageURI(data?.data)
        }

        else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PICK_REQUEST){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoButton.setImageBitmap(imageBitmap)
        }
    }
}
