package fr.cabrolalexis.velo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fr.cabrolalexis.velo.model.City
import fr.cabrolalexis.velo.repository.CityRepository
import fr.cabrolalexis.velo.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject


class HomeViewModel(private val cityRepository: CityRepository) : BaseViewModel() {

    val city: BehaviorSubject<List<City>> = BehaviorSubject.create()

    init {
        cityRepository.fetchCity()
                .subscribe { }
                .disposedBy(disposeBag)
        cityRepository.listCity
                .subscribe { city.onNext(it) }
                .disposedBy(disposeBag)
    }

    class Factory(private val cityRepository: CityRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(cityRepository) as T
        }
    }
}