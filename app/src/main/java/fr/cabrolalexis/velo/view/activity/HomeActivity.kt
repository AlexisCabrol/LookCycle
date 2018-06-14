package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.model.City
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.CityListAdapter
import fr.cabrolalexis.velo.view.adapter.ItemDecoration
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.generic.instance
import timber.log.Timber

class HomeActivity: BaseActivity(), CityListAdapter.CityListAdapterCallback {

    private val viewModel: HomeViewModel by instance(arg = this)
    private val adapter = CityListAdapter(this)

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvStationList.adapter = adapter
        rvStationList.layoutManager = LinearLayoutManager(this)
        rvStationList.addItemDecoration(ItemDecoration(ContextCompat.getDrawable(applicationContext, R.drawable.item_decoration)!!))
        viewModel.fetchCity()

        about.clicks()
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ startActivity(AboutActivity.createIntent(this)) })

        viewModel.loadCityState
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ onCityStateChange(it)}, { Timber.e(it)})
    }

    //region network

    private fun onCityStateChange(netStatus: NetworkEvent) {
        when(netStatus) {
            NetworkEvent.None -> {
                onCityStateNone()
            }
            NetworkEvent.InProgress -> {
                onCityStateProgress()
            }
            is NetworkEvent.Error -> {
                onCityStateError()
            }
            NetworkEvent.Success -> {
                onCityStateSuccess()
            }
        }
    }

    private fun onCityStateNone() {
        pbHome.visibility = GONE

    }

    private fun onCityStateProgress() {
        pbHome.visibility = VISIBLE

    }

    private fun onCityStateError() {
        pbHome.visibility = GONE
        Snackbar.make(clRoot, R.string.errorLoadCityList, Snackbar.LENGTH_SHORT).show()

    }

    private fun onCityStateSuccess() {
        pbHome.visibility = GONE
        setupListCity()
    }

    //endregion

    private fun setupListCity() {
        val listCity = viewModel.city.value
        if(!listCity.isEmpty()) {
            adapter.submitList(listCity)
        }

    }

    //region Callback

    override fun onItemClick(city: City) {
        startActivity(CityDetailsActivity.createIntent(this, city.name))
    }

    //endregion
}
