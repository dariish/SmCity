package ipvc.estg.smcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarNota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_editar)

        var editarTitulo: EditText = findViewById(R.id.editar_titulo)
        var editarDescricao: EditText = findViewById(R.id.editar_descricao)

        var id = intent.getStringExtra(PARAM_ID)
        var titulo = intent.getStringExtra(PARAM1_TITULO)
        var descricao = intent.getStringExtra(PARAM2_DESCRICAO)

        editarTitulo.setText(titulo.toString())
        editarDescricao.setText(descricao.toString())

        val button_remove = findViewById<Button>(R.id.button_eliminar)
        button_remove.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(PARAM_ID, id.toString())
            replyIntent.setAction("REMOVE")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        val button_update = findViewById<Button>(R.id.button_atualizar)
        button_update.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(PARAM_ID, id)
            if (TextUtils.isEmpty(editarTitulo.text) || TextUtils.isEmpty(editarDescricao.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val titulo = editarTitulo.text.toString()
                replyIntent.putExtra(PARAM1_TITULO, titulo)

                val descricao = editarDescricao.text.toString()
                replyIntent.putExtra(PARAM2_DESCRICAO, descricao)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

}