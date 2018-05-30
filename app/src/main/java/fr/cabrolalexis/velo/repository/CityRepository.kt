package fr.cabrolalexis.velo.repository

import fr.cabrolalexis.velo.BuildConfig
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.data.VLService
import fr.cabrolalexis.velo.model.City
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class CityRepository(private val service: VLService) {

    private val apiKey: String = BuildConfig.API_KEY
    val listCity: BehaviorSubject<List<City>> = BehaviorSubject.create()

    fun fetchCity(): Flowable<List<City>> {
        val obs = service.getContracts(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    listCity.onNext(it)
                }

                .share()
        return obs
    }

}