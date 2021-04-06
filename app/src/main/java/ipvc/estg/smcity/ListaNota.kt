package ipvc.estg.smcity


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.smcity.adapters.NotasAdapter
import ipvc.estg.smcity.entities.Notas
import ipvc.estg.smcity.viewModel.NotasViewModel


var PARAM_ID = "id"
var PARAM1_TITULO = "titulo"
var PARAM2_DESCRICAO = "descricao"

class ListaNota  : AppCompatActivity(), CellClickListener{
    private lateinit var notasViewModel: NotasViewModel
    private val newNotaActivityRequestCode = 1
    private val updateNotaActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_lista)

        // recycle View
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NotasAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
        notasViewModel.allNotas.observe(this,{notas->
            notas?.let{adapter.setNotas(it)}
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@ListaNota, AddNota::class.java)
            startActivityForResult(intent, newNotaActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null && data.action == "REMOVE") {
            var id = Integer.parseInt(data?.getStringExtra(PARAM_ID))
            notasViewModel.deleteById(id)
            Toast.makeText(this, "Nota eliminada.", Toast.LENGTH_SHORT).show()
            return
        }


        if (requestCode == newNotaActivityRequestCode) {
            //Inserir Nota
            if (requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {
                var titulo = data?.getStringExtra(AddNota.EXTRA_REPLY_TITULO).toString()
                var descricao = data?.getStringExtra(AddNota.EXTRA_REPLY_DESCRICAO).toString()
                var nota = Notas(nota = titulo, descricao = descricao)
                notasViewModel.insert(nota)
                Toast.makeText(this, "Nota editada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                        applicationContext,
                        "Todos os campos não estão preenchidos ",
                        Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == updateNotaActivityRequestCode) {
            // Editar nota
            if (requestCode == updateNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {
                var titulo = data?.getStringExtra(PARAM1_TITULO).toString()
                var descricao = data?.getStringExtra(PARAM2_DESCRICAO).toString()
                var id = Integer.parseInt(data?.getStringExtra(PARAM_ID))
                notasViewModel.updateById(titulo, descricao, id)
                Toast.makeText(applicationContext, "Nota alterada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Todos os campos não estão preenchidos ", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onCellClickListener(data: Notas) {
        val id = data.id.toString()
        val titulo = data.nota
        val descricao = data.descricao
        val intent = Intent(this, EditarNota::class.java).apply {
            putExtra(PARAM_ID, id)
            putExtra(PARAM1_TITULO, titulo)
            putExtra(PARAM2_DESCRICAO, descricao)
        }
        startActivityForResult(intent, updateNotaActivityRequestCode)
    }




}