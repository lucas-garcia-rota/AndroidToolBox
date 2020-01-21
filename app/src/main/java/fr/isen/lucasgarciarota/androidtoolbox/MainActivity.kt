package fr.isen.lucasgarciarota.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login.setOnClickListener {
            val identifiant = input1.text.toString()
            val mdp = input2.text.toString()
            var message = "Authentification validé pour $identifiant"

            if(identifiant.equals("admin") && mdp.equals("123")){
                val toast = Toast.makeText(this,message, Toast.LENGTH_LONG)
                toast.show()
                toast.setGravity(Gravity.TOP,0,200)
                val changePage = Intent(this, HomeActivity::class.java)
                startActivity(changePage)
            }
            else{
                message = "Authentification échoué"
                val toast2 = Toast.makeText(this,message, Toast.LENGTH_LONG)
                toast2.show()
                toast2.setGravity(Gravity.TOP,0,200)
            }
        }

    }

}
