package fr.isen.lucasgarciarota.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val formInput: SharedPreferences = getSharedPreferences("FormInfo", Context.MODE_PRIVATE)

        val textView: TextView = findViewById(R.id.dateText)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)

        }

        dateButton.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        submitButton.setOnClickListener {
            jsonForm(formInput)
        }

        informationsButton.setOnClickListener {
            popUp(formInput, 21)
        }
    }

    fun popUp(formInput: SharedPreferences, age: Int) {
        var gson: Gson = Gson()

        var json: String = formInput.getString("Profile", "").toString()
        var user: User
        if (formInput != null) {
            user = gson.fromJson(json, User::class.java)


            var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

            alertDialogBuilder
                .setTitle("Informations")
                .setMessage("Nom :" + user.name + "\nPrenom:" + user.firstname + "\nDate de naissance:" + user.birthDate + "\nAge:21")


            var alertDialog: AlertDialog = alertDialogBuilder.create()

            alertDialog.show()

            print(age)

        }

    }

    fun jsonForm(formInput: SharedPreferences){
        var json: Gson = Gson()
        val user: User = User(this.nomText.text.toString(), this.prenomText.text.toString(), this.dateText.text.toString())
        val editor: SharedPreferences.Editor = formInput.edit()
        editor.putString("Personne", json.toJson(user))
        editor.apply()

        this.nomText.setText("")
        this.prenomText.setText("")
        this.dateText.setText("")
    }
}
