package ipvc.estg.smcity;

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.smcity.adapters.NotasAdapter
import ipvc.estg.smcity.viewModel.NotasViewModel


class AddNota  : AppCompatActivity() {

    private lateinit var editTituloView: EditText
    private lateinit var editDescricaoView: EditText

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_add)

        editTituloView = findViewById(R.id.edit_titulo)
        editDescricaoView = findViewById(R.id.edit_descricao)



        val button = findViewById<Button>(R.id.button_save)
            button.setOnClickListener{
                val replyIntent = Intent()
                if(TextUtils.isEmpty(editTituloView.text) || TextUtils.isEmpty(editDescricaoView.text)){
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                }else {
                    val id = editTituloView.id
                    replyIntent.putExtra(EXTRA_REPLY_ID, id)

                    val titulo = editTituloView.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY_TITULO, titulo)

                    val descricao = editDescricaoView.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY_DESCRICAO, descricao)


                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
    }

    companion object{
        const val EXTRA_REPLY_ID = "id"
        const val EXTRA_REPLY_TITULO = "titulo"
        const val EXTRA_REPLY_DESCRICAO = "descricao"
    }

}