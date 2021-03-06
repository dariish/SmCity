package ipvc.estg.smcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    //teste
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

        //Cria a variável shareed preferences
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        remember = sharedPreferences.getBoolean("remember", false)

        //Verifica o Shared Preferences ativo. Redirecciona para a proxima página imediatamente.
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
            login(loginButton)
        }
    }


    private fun login(view : View){
        // Crio o retrofit
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        // Variáveis que guardam os textBoxs e o boolean do checkBox
        val nome = editNome.text.toString()
        val password = editPassword.text.toString()
        val checked = checkboxRemember.isChecked

        // Faço o request ao EndPoint
        val call  = request.login(nome = nome , password = password)

        call.enqueue(object : Callback<OutputLogin> {
            override fun onResponse(call : Call<OutputLogin>, response: Response<OutputLogin>){
                if(response.isSuccessful){
                    // Guardo o response body do OutputLogin da variável c
                    val c : OutputLogin = response.body()!!
                            if(nome.isEmpty() || password.isEmpty()){
                                Toast.makeText(this@MainActivity, "Campos vazios", Toast.LENGTH_SHORT).show()
                            }else {
                                if (c.status == "false") {
                                    Toast.makeText(this@MainActivity, c.MSG, Toast.LENGTH_LONG).show()
                                } else {
                                    // Aplico as novas variáveis ao editor sharePreferences e aplico.
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