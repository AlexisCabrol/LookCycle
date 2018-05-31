package fr.cabrolalexis.velo.repository

import fr.cabrolalexis.velo.BuildConfig
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.data.VLService
import fr.cabrolalexis.velo.model.Station
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class StationRepository(private val service: VLService) {

    private val apiKey: String = BuildConfig.API_KEY
    val listStation: BehaviorSubject<List<Station>> = BehaviorSubject.create()

    fun getStation(name: String): Flowable<NetworkEvent> {
        val obs = service.getStationListOfCity(name, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    listStation.onNext(it)
                }
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn{ NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
        return obs
    }

    fun refreshStation(name: String): Flowable<NetworkEvent> {
        val obs = service.getStationListOfCity(name, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    listStation.onNext(it)
                }
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
        return obs
    }
}