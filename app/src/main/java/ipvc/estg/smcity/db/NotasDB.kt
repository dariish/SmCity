package ipvc.estg.smcity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.smcity.entities.Notas
import ipvc.estg.smcity.dao.NotasDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Database(entities = arrayOf(Notas::class), version = 1, exportSchema = false)
public abstract class NotasDB : RoomDatabase() {
    abstract fun notasDao(): NotasDao

   private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch{
                    var notasDao = database.notasDao()

                    notasDao.deleteAll()

                    var nota = Notas(1, "titulo", "descricao")
                    notasDao.insert(nota)
                }
            }
        }
    }



companion object {
    @Volatile
    private var INSTANCE: NotasDB? = null

    fun getDatabase(context: Context): NotasDB {
        val tempInstance = INSTANCE
        if(tempInstance != null){
            return tempInstance
        }
        synchronized(this){
            val instance =  Room.databaseBuilder(
                context.applicationContext,
                NotasDB::class.java,
                    "notas_database",
            )  .build()

            //    .addCallback(WordDatabaseCallback(scope))


            INSTANCE = instance
            return instance
        }
    }


}

}