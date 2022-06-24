package net.faracloud.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        navHostFragment.navController.graph = graph
    }
}



/*
 override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        println("never here")
        loge("never here")
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (result != null) {

            //Check to see if QR Code has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //QR Code contains some data
                try {

                    //Convert the QR Code Data to JSON
                    val obj = JSONObject(result.contents)
                    //Set up the TextView Values using the data from JSON
                    Log.e("متن ", obj.toString())


                    val nameValue = obj.getString("name")
                    val tokenValue = obj.getString("token")
                    loge("nameValue " + nameValue)
                    loge("tokenValue " + tokenValue)

                    name.setText(nameValue)
                    token.setText(tokenValue)

                } catch (e: JSONException) {
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent)
        }
        /*(supportFragmentManager.findFragmentByTag("Add_New_Provider_Tag") as Fragment?)?.onActivityResult(
            requestCode,
            resultCode,
            intent
        )*/
    }
 */