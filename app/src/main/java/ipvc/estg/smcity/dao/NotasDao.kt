package ipvc.estg.smcity.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ipvc.estg.smcity.entities.Notas

@Dao
interface NotasDao {
    @Query("SELECT * from notas_table ORDER BY titulo ASC")
    fun getAlphabetizedNotas(): LiveData<List<Notas>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Notas)

    @Query("DELETE FROM notas_table")
    suspend fun deleteAll()

    @Query("DELETE FROM notas_table WHERE id == :id")
    fun deleteById(id: Int)

    @Query("UPDATE notas_table SET titulo=:titulo, descricao=:descricao WHERE id == :id")
    suspend fun updateById(titulo: String, descricao: String, id: Int)

}