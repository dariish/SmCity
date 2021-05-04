package ipvc.estg.smcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import ipvc.estg.smcity.api.EndPoints
import ipvc.estg.smcity.api.ServiceBuilder
import ipvc.estg.smcity.api.Utilizador
import okhttp3.Call
import okhttp3.Response
import javax.security.auth.callback.Callback


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var remember = false
    private lateinit var checkboxRemember : EditText

    private lateinit var editNome : EditText
    private lateinit var editPassword : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editNome = findViewById(R.id.username)
        editPassword = findViewById(R.id.password)

        checkboxRemember = findViewById(R.id.passCheck)

        //Lembrar login ou não
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        remember = sharedPreferences.getBoolean("remember", false)
        if(remember){
            val intent = Intent(this@MainActivity, Menu::class.java)
            startActivity(intent)
            finish()
        }



        val button = findViewById<Button>(R.id.buttonNotas)
            button.setOnClickListener{
                val intent = Intent(this, ListaNota::class.java)
                startActivity(intent)
            }
    }


    fun login(view: View){
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val nomne = editNome.text.toString()
        val password = editPassword.text.toString()
        val checked :  Boolean = checkboxRemember.isChecked
        val call  = request.login(nome = nome , password = password)

        call.enqueue(object : Callback<OutputLogin>{
            override fun onResponse(call : Call<List<Utilizador>>, response: Response<List<Utilizador>>)


        })

    }


}