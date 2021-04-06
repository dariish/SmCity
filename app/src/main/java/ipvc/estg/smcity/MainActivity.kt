package ipvc.estg.smcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.buttonNotas)
            button.setOnClickListener{
                val intent = Intent(this, ListaNota::class.java)
                startActivity(intent)
            }
    }


}