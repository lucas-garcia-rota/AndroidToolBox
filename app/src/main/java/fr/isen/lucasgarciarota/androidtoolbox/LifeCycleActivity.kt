package fr.isen.lucasgarciarota.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_life_cycle.*

class LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        notification("onCreate",true)

        val newFragment = LifeCycleFragment()
        val newFragment2 = LifeCycleFragment2()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.lifeCycleContainer, newFragment)
        transaction.commit()

        buttonFragment.setOnClickListener {
            if(newFragment.isResumed){
                Log.d("TAG","Fragment 1 is resumed")
                supportFragmentManager.beginTransaction().replace(R.id.lifeCycleContainer, newFragment2).commit()
            }
            else{
                Log.d("TAG","Fragment 2 is resumed")
                supportFragmentManager.beginTransaction().replace(R.id.lifeCycleContainer, newFragment).commit()
            }
        }
    }


    private fun notification(message: String, isActive: Boolean){
        if(isActive)
            lifeCycleText.text=message
        else
            Log.d("TAG", message)
    }

    override fun onStart() {
        super.onStart()
        notification("onStart", true)
    }

    override fun onResume() {
        super.onResume()
        notification("onResume",true)
    }

    override fun onPause() {
        super.onPause()
        notification("onPause", false)
    }

    override fun onStop() {
        super.onStop()
        notification("onStop", false)
    }

    override fun onRestart() {
        super.onRestart()
        notification("onRestart", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG)
    }
}
