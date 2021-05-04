package ipvc.estg.smcity.api

data class Utilizador(
    val id: Int,
    val nome: String,
    val password: String,
    val ocorrencias: Ocorrencia
)
