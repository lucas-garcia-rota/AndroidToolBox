package fr.isen.lucasgarciarota.androidtoolbox

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("Login"," ") != " "){
            val changePage = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(changePage)
        }

        loginButtonn.setOnClickListener {
            val identifiant = inputLogin.text.toString()
            val mdp = inputPass.text.toString()
            var message = "Authentification validé pour $identifiant"

            if(identifiant.equals("admin") && mdp.equals("123")){

                val editor = getSharedPreferences("Pref", Context.MODE_PRIVATE).edit()
                editor.putString("Login", identifiant)
                editor.putString("Pass", mdp)
                editor.apply()

                val toast = Toast.makeText(this,message, Toast.LENGTH_LONG)
                toast.show()
                toast.setGravity(Gravity.TOP,0,200)
                val changePage = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
