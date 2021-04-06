package ipvc.estg.smcity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.smcity.CellClickListener
import ipvc.estg.smcity.R
import ipvc.estg.smcity.entities.Notas

class NotasAdapter internal constructor(
        context: Context,
        private val cellClickListener: CellClickListener
        ) : RecyclerView.Adapter<NotasAdapter.NotasViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Notas>()

    class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notatItemViewTitutlo: TextView = itemView.findViewById(R.id.titulo)
        val notatItemViewDescricao: TextView = itemView.findViewById(R.id.descricao)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val itemView = inflater.inflate(R.layout.recycleline, parent, false)
        return NotasViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val current = notas[position]
        holder.notatItemViewTitutlo.text = current.nota
        holder.notatItemViewDescricao.text = current.descricao

        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(current)
        }

    }

    internal fun setNotas(notas: List<Notas>){
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() = notas.size

}
