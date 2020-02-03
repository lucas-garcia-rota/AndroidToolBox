package fr.isen.lucasgarciarota.androidtoolbox

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_web_services.*

class WebServicesActivity : AppCompatActivity() {

    val url = "https://randomuser.me/api/?inc=gender,name,location,email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, Response.Listener {

            response ->
            textPerson.text = response.toString()

        }, Response.ErrorListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
        })

        queue.add(request)
    }
}
