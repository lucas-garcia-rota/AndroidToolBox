package fr.isen.lucasgarciarota.androidtoolbox

import android.app.Activity
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
import androidx.core.content.FileProvider
import androidx.core.graphics.set
import kotlinx.android.synthetic.main.activity_permission.*
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val photoButton: ImageView = findViewById(R.id.photoButton)

        photoButton.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if(data != null){
                val imageUri: Uri = data?.data
                val selectedImage: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                photoButton.setImageBitmap(selectedImage)
            }
        }
    }
}
