package ipvc.estg.smcity.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


    //Utilizador

    @GET("api/utilizador")
    fun getUtilizadores(): Call<List<Utilizador>>

    @GET("api/utilizador/{id}")
    fun getUtilizadorById(@Path("id") id:Int):Call<Utilizador>

    @FormUrlEncoded
    @POST("api/login")
    fun login(@Field("nome") nome: String?, @Field("password") password: String?): Call<OutputLogin>




    //Ocorrencia
    @GET("api/ocorrencia")
    fun getOcorrencias(): Call<List<Ocorrencia>>

    @GET("api/ocorrencia/{id}")
    fun getOcorrenciaById(@Path("id") id: Int): Call<Ocorrencia>




}