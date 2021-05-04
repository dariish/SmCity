package ipvc.estg.smcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.smcity.api.EndPoints
import ipvc.estg.smcity.api.OutputLogin
import ipvc.estg.smcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var remember = false
    private lateinit var checkboxRemember : CheckBox

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

        val notasButton = findViewById<Button>(R.id.buttonNotas)
        notasButton.setOnClickListener{
                val intent = Intent(this@MainActivity, ListaNota::class.java)
                startActivity(intent)
                finish()
            }

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener{
            login()
        }

    }


    private fun login(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val nome = editNome.text.toString()
        val password = editPassword.text.toString()
        val checked = checkboxRemember.isChecked
        val call  = request.login(nome = nome , password = password)

        call.enqueue(object : Callback<OutputLogin> {
            override fun onResponse(call : Call<OutputLogin>, response: Response<OutputLogin>){
                if(response.isSuccessful){
                    val c : OutputLogin = response.body()!!
                            if(nome.isEmpty() || password.isEmpty()){
                                Toast.makeText(this@MainActivity, "Campos vazios", Toast.LENGTH_SHORT).show()
                            }else {
                                if (c.status == "false") {
                                    Toast.makeText(this@MainActivity, c.MSG, Toast.LENGTH_LONG).show()
                                } else {
                                    val sharedPreferences_edit: SharedPreferences.Editor = sharedPreferences.edit()
                                    sharedPreferences_edit.putString("nome", nome)
                                    sharedPreferences_edit.putString("password", password)
                                    sharedPreferences_edit.putBoolean("remember", checked)
                                    sharedPreferences_edit.apply()

                                    val intent = Intent(this@MainActivity, Menu::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                }
            }
            override fun onFailure(call: Call<OutputLogin>, t: Throwable){
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


}