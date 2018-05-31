package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.CityDetailsViewPagerAdapter
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.view.fragment.MapsFragment
import fr.cabrolalexis.velo.view.fragment.StationListFragment
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.activity_city_details.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.net.NetworkInterface

class CityDetailsActivity : BaseActivity() {

    private val viewModel: CityDetailsViewModel by instance(arg = this)

    companion object {
        val EXTRA_NAME_CITY = "EXTRA_NAME_CITY"

        fun createIntent(context: Context, name: String): Intent {
            val intent = Intent(context, CityDetailsActivity::class.java)
            intent.putExtra(EXTRA_NAME_CITY, name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)


        val name = intent.getStringExtra(EXTRA_NAME_CITY)
        titleCity.text = name
        viewModel.fetchStation(name)

        viewModel.loadStationState
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ onLoadStationStationChange(it)},{ Timber.e(it)})

        back.clicks()
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ onBackPressed()})


    }

    //region network status

    private fun onLoadStationStationChange(netStatus: NetworkEvent) {
        when(netStatus) {
            NetworkEvent.None -> {
                onLoadStationStateNone()
            }
            NetworkEvent.InProgress -> {
                onLoadStationStateProgress()
            }
            NetworkEvent.Success -> {
                onLoadStationStateSuccess()
            }
            is NetworkEvent.Error -> {
                onLoadStationStateError()
            }
        }
    }

    private fun onLoadStationStateNone() {
        pbDetails.visibility = GONE
    }

    private fun onLoadStationStateProgress() {
        pbDetails.visibility = VISIBLE
    }

    private fun onLoadStationStateError() {
        pbDetails.visibility = GONE
        Snackbar.make(clDetails, R.string.errorLoadStationList, Snackbar.LENGTH_SHORT).show()
    }

    private fun onLoadStationStateSuccess() {
        pbDetails.visibility = GONE
        setupViewPager()
    }

    //endregion


    private fun setupViewPager() {
        val adapter = CityDetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MapsFragment.newInstance(), getString(R.string.titleMap))
        adapter.addFragment(StationListFragment.newInstance(), getString(R.string.titleStationList))
        vpCity.adapter = adapter
        tlCityDetails.setupWithViewPager(vpCity)

    }
}