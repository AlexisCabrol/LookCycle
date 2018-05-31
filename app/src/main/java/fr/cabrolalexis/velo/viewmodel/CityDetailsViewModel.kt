package fr.cabrolalexis.velo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fr.cabrolalexis.velo.model.Station
import fr.cabrolalexis.velo.repository.StationRepository
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class CityDetailsViewModel(private val stationRepository: StationRepository): BaseViewModel() {

    val station: BehaviorSubject<List<Station>> = BehaviorSubject.create()

    fun fetchStation(name: String) {
        stationRepository.fetchStation(name)
                .subscribe({ station.onNext(it) }, { Timber.e(it)})
    }



    class Factory(private val stationRepository: StationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CityDetailsViewModel(stationRepository) as T
        }
    }

}