package fr.cabrolalexis.velo

import android.app.Application
import android.content.Context
import fr.cabrolalexis.velo.injection.networkModule
import fr.cabrolalexis.velo.injection.repositoryModule
import fr.cabrolalexis.velo.injection.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class VLApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        bind<Application>() with singleton {this@VLApplication }
        bind<Context>() with singleton { instance<Application>() }

        import(networkModule)
        import(repositoryModule)
        import(viewModelModule)


    }
}