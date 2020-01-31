package fr.isen.lucasgarciarota.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class FormActivity : AppCompatActivity() {

    val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val gson = Gson()
        val user: User = User()
        var json: String = gson.toJson(user)

        val textView: TextView = findViewById(R.id.dateText)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())


        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            textView.text = sdf.format(cal.time)

        }

        dateButton.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        submitButton.setOnClickListener {
            user.name = nomText.text.toString()
            user.firstname = prenomText.text.toString()
            user.birthDate = dateText.text.toString()

            json = gson.toJson(user)
            val toast = Toast.makeText(this, "Informations enregistrées", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 200)
            toast.show()

            nomText.setText("")
            prenomText.setText("")
            dateText.setText("")

        }

        informationsButton.setOnClickListener {

            val showJson = gson.fromJson(json, User::class.java)
            var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            val birthday = SimpleDateFormat("dd.MM.yyyy").parse(user.birthDate).time
            val now  = Calendar.getInstance().timeInMillis
            val diff = now - birthday
            val jour = TimeUnit.MILLISECONDS.toDays(diff)

            val date = jour / 365

            alertDialogBuilder
                .setTitle("Informations")
                .setMessage("Nom : " + showJson.name + "\nPrenom : " + showJson.firstname + "\nDate de naissance : " + showJson.birthDate + "\nAge : " + date)


            var alertDialog: AlertDialog = alertDialogBuilder.create()

            alertDialog.show()

        }
    }
}
