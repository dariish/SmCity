package ipvc.estg.smcity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas_table")
data class Notas(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                @ColumnInfo(name = "titulo") val nota: String,
                @ColumnInfo(name = "Descricao") val descricao: String )

/*
data class Notas {

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
            @ColumnInfo(name = "titulo") val city: String,
                        @ColumnInfo(name = "descricao") val descricao: String
}
*/
