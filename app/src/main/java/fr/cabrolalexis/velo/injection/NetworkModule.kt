package fr.cabrolalexis.velo.injection

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.cabrolalexis.velo.BuildConfig
import fr.cabrolalexis.velo.data.VLService
import fr.cabrolalexis.velo.model.Station
import fr.cabrolalexis.velo.model.typeadapter.StationTypeAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = Kodein.Module(allowSilentOverride = true) {
    val tagLoggingInterceptor = "loggingInterceptor"
    val tagNetworkErrorInterceptor = "networkErrorInterceptor"
    val tagGsonConverterFactory = "gsonConverterFactory"
    val tagRxJavaCallAdapterFactory = "rxJavaCallAdapterFactory"
    val tagBaseUrl = "baseUrl"

    constant(tagBaseUrl) with BuildConfig.API_URL

    bind<Interceptor>(tagLoggingInterceptor) with singleton {
        val hli = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            hli.level = HttpLoggingInterceptor.Level.BODY
        } else {
            hli.level = HttpLoggingInterceptor.Level.NONE
        }
        hli
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
                .addInterceptor(instance(tagLoggingInterceptor))
//                .addInterceptor(instance(tagNetworkErrorInterceptor))
                .build()
    }

    bind<Gson>() with singleton {
        GsonBuilder()
                .registerTypeAdapter(StationTypeAdapter::class.java, StationTypeAdapter())
                .create()
    }


    bind<Converter.Factory>(tagGsonConverterFactory) with singleton { GsonConverterFactory.create(instance()) }

    bind<CallAdapter.Factory>(tagRxJavaCallAdapterFactory) with singleton { RxJava2CallAdapterFactory.create() }

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<VLService>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(instance<String>(tagBaseUrl))
                .client(instance())
                .addConverterFactory(instance(tagGsonConverterFactory))
                .addCallAdapterFactory(instance(tagRxJavaCallAdapterFactory))
                .build()
                .create(VLService::class.java)
    }

    bind<ConnectivityManager>() with provider {
        instance<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}