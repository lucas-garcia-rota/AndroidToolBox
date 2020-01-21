package fr.isen.lucasgarciarota.androidtoolbox

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE)

        deconnexionButton.setOnClickListener {
            var editor = sharedPreferences.edit()
            editor.putString("Login", " ")
            editor.apply()

            var intent = Intent(this, LoginsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        imageView3.setOnClickListener {
            val changePage = Intent(this, LifeCycleActivity::class.java)
            startActivity(changePage)
        }

        lifeCycle.setOnClickListener {
            val changePage = Intent(this, LifeCycleActivity::class.java)
            startActivity(changePage)
        }

        saveImage.setOnClickListener {
            val changePage = Intent(this, FormActivity::class.java)
            startActivity(changePage)
        }

        saveText.setOnClickListener {
            val changePage = Intent(this, FormActivity::class.java)
            startActivity(changePage)
        }
    }
}
