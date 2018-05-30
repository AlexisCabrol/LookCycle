package fr.cabrolalexis.velo.data

import fr.cabrolalexis.velo.model.City
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface VLService {

    //region city

    @GET("contracts")
    fun getContracts(@Query("apiKey") key: String): Flowable<List<City>>
    //endregion
}