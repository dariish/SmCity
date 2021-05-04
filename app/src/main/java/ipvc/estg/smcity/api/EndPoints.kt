package ipvc.estg.smcity.api

import android.telecom.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/api/utilizador")
    fun getUtilizadores(): Call<List<Utilizador>>

    @FormUrlEncoded
    @POST("api/login")
    fun postLogin(@Field("nome") nome: String?, @Field("password") password: String?): Call<OutputLogin>




    @GET("/api/ocorrencia")
    fun getOcorrencias(): Call<List<Ocorrencia>>

    @GET("/api/ocorrencia/{id}")
    fun getOcorrenciaById(@Path("id") id: Int): Call<Ocorrencia>




}