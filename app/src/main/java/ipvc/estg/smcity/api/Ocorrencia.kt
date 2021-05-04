package ipvc.estg.smcity.api

data class Ocorrencia(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val descricao: String,
    val utilizador_id: Int
)