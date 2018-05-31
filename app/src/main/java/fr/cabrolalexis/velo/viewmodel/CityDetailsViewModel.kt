package fr.cabrolalexis.velo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.model.Station
import fr.cabrolalexis.velo.repository.StationRepository
import fr.cabrolalexis.velo.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class CityDetailsViewModel(private val stationRepository: StationRepository) : BaseViewModel() {

    private val currentNameCity: BehaviorSubject<String> = BehaviorSubject.create()
    val station: BehaviorSubject<List<Station>>
        get() {
            return stationRepository.listStation
        }

    val refreshState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)
    val loadStationState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    fun fetchStation(name: String) {
        currentNameCity.onNext(name)
        stationRepository.getStation(currentNameCity.value)
                .subscribe({ loadStationState.onNext(it) }, { Timber.e(it) })
    }

    fun refresh() {
        stationRepository.refreshStation(currentNameCity.value)
                .subscribe({ refreshState.onNext(it)}, { Timber.e(it) })
    }


    class Factory(private val stationRepository: StationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CityDetailsViewModel(stationRepository) as T
        }
    }

}