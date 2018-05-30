package fr.cabrolalexis.velo.injection

import android.arch.lifecycle.ViewModelProviders
import fr.cabrolalexis.velo.repository.CityRepository
import fr.cabrolalexis.velo.view.activity.HomeActivity
import fr.cabrolalexis.velo.viewmodel.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module {

    bind<HomeViewModel.Factory>() with provider { HomeViewModel.Factory(instance<CityRepository>()) }
    bind<HomeViewModel>() with factory { activity: HomeActivity ->
        ViewModelProviders.of(activity, instance<HomeViewModel.Factory>())
                .get(HomeViewModel::class.java)
    }
}