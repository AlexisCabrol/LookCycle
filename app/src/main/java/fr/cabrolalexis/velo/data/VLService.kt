package fr.cabrolalexis.velo.data

import fr.cabrolalexis.velo.model.City
import fr.cabrolalexis.velo.model.Station
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VLService {

    //region city

    @GET("contracts")
    fun getContracts(@Query("apiKey") key: String): Flowable<List<City>>

    //endregion

    //region station

    @GET("stations?contract={contract_name}")
    fun getStationListOfCity(@Path("contract_name") cityName: String, @Query("apiKey") key: String): Flowable<List<Station>>

    //endregion
}