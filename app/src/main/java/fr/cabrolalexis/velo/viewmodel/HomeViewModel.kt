package fr.cabrolalexis.velo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.model.City
import fr.cabrolalexis.velo.repository.CityRepository
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber


class HomeViewModel(private val cityRepository: CityRepository) : BaseViewModel() {

    val loadCityState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)
    val city: BehaviorSubject<List<City>>
        get() {
            return cityRepository.listCity
        }

    fun fetchCity() {
        cityRepository.fetchCity()
                .subscribe({ loadCityState.onNext(it) }, { Timber.e(it) })
    }

    class Factory(private val cityRepository: CityRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(cityRepository) as T
        }
    }
}