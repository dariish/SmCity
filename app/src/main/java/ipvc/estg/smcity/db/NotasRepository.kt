package ipvc.estg.smcity.db

import androidx.lifecycle.LiveData
import ipvc.estg.smcity.dao.NotasDao
import ipvc.estg.smcity.entities.Notas

class NotasRepository(private val notasDao: NotasDao) {

    val allNotas: LiveData<List<Notas>> = notasDao.getAlphabetizedNotas()

    suspend fun insert(notas: Notas){
        notasDao.insert(notas)
    }

    fun deleteById(id: Int){
        notasDao.deleteById(id)
    }

    suspend fun updateById(titulo: String, descricao: String, id: Int){
        notasDao.updateById(titulo, descricao, id)
    }
}