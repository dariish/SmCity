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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)


        // Logout
        // Chamar o SharedPreferences do Main Activity
        sPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE )
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




    }

}