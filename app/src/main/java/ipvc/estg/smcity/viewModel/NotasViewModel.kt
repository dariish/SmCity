package ipvc.estg.smcity.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import ipvc.estg.smcity.db.NotasDB

import ipvc.estg.smcity.db.NotasRepository
import ipvc.estg.smcity.entities.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.lang.IllegalArgumentException

class NotasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NotasRepository
    val allNotas: LiveData<List<Notas>>

    init{
        // atenção, não esta igual aos outros dois. linha de codigo perigo.
        val notasDao = NotasDB.getDatabase(application).notasDao()
        repository = NotasRepository(notasDao)
        allNotas = repository.allNotas
    }

    fun insert(notas: Notas) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(notas)
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteById(id)
    }
    fun updateById(titulo: String, descricao: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateById(titulo, descricao, id)
    }

}


/*

    val allNotas: LiveData<List<Notas>>

    fun insert(notas: Notas) = viewModelScope.launch {
        repository.insert(notas)
    }
}
class NotasViewModelFactory(private val repository: NotasRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotasViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NotasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

 */