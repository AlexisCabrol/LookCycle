package fr.cabrolalexis.velo.injection

import android.arch.lifecycle.ViewModelProviders
import fr.cabrolalexis.velo.repository.CityRepository
import fr.cabrolalexis.velo.repository.StationRepository
import fr.cabrolalexis.velo.view.activity.CityDetailsActivity
import fr.cabrolalexis.velo.view.activity.HomeActivity
import fr.cabrolalexis.velo.view.fragment.StationListFragment
import fr.cabrolalexis.velo.viewmodel.HomeViewModel
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module {

    //region Activity

    bind<HomeViewModel.Factory>() with provider { HomeViewModel.Factory(instance<CityRepository>()) }
    bind<HomeViewModel>() with factory { activity: HomeActivity ->
        ViewModelProviders.of(activity, instance<HomeViewModel.Factory>())
                .get(HomeViewModel::class.java)
    }

    bind<CityDetailsViewModel.Factory>() with provider { CityDetailsViewModel.Factory(instance<StationRepository>()) }
    bind<CityDetailsViewModel>() with factory { activity: CityDetailsActivity ->
        ViewModelProviders.of(activity, instance<CityDetailsViewModel.Factory>())
                .get(CityDetailsViewModel::class.java)
    }

    //endregion

}