package ipvc.estg.smcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Menu  : AppCompatActivity() {


    private lateinit var sPreferences: SharedPreferences
    private lateinit var logout : Button
    private lateinit var notas : Button
    private lateinit var mapa: Button
    private lateinit var addOcorrencia: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)


        // Logout
        // Chamar o SharedPreferences do Main Activity
        sPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE )

        //Button Logout
        logout = findViewById(R.id.logout)
        logout.setOnClickListener{
            val editor: SharedPreferences.Editor = sPreferences.edit()
            // clear e Apply deste novo editor
            editor.clear()
            editor.apply()

            val intent = Intent(this@Menu, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Button notas
        notas = findViewById<Button>(R.id.notas)
        notas.setOnClickListener{
            val intent = Intent(this@Menu, ListaNota::class.java)
            startActivity(intent)
        }


        //Button mapa
        mapa = findViewById(R.id.mapa)
        mapa.setOnClickListener{
            val intent = Intent(this@Menu, MapsActivity::class.java)
                startActivity(intent)
            }

        //Button adicionar Ocorrencia
        addOcorrencia = findViewById(R.id.reportar)
        addOcorrencia.setOnClickListener{
            val intent = Intent(this@Menu, AddOcorrencia::class.java)
            startActivity(intent)
        }


        }

    }

