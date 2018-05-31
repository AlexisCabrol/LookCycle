package fr.cabrolalexis.velo.repository

import fr.cabrolalexis.velo.BuildConfig
import fr.cabrolalexis.velo.data.VLService
import fr.cabrolalexis.velo.model.Station
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class StationRepository(private val service: VLService) {

    private val apiKey: String = BuildConfig.API_KEY
    val listStation: BehaviorSubject<List<Station>> = BehaviorSubject.create()

    fun fetchStation(name: String): Flowable<List<Station>> {
        val obs = service.getStationListOfCity(name, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    listStation.onNext(it)
                }
                .share()
        return obs
    }
}