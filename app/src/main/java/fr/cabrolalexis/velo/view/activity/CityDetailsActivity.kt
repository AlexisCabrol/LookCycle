package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.CityDetailsViewPagerAdapter
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.view.fragment.MapsFragment
import fr.cabrolalexis.velo.view.fragment.StationListFragment
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.activity_city_details.*
import org.kodein.di.generic.instance

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
        setupViewPager()

        val name = intent.getStringExtra(EXTRA_NAME_CITY)
        titleCity.text = name
        viewModel.fetchStation(name)

        back.clicks()
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ onBackPressed()})
    }


    private fun setupViewPager() {
        val adapter = CityDetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(StationListFragment.newInstance(), getString(R.string.titleStationList))
        adapter.addFragment(MapsFragment.newInstance(), getString(R.string.titleMap))
        vpCity.adapter = adapter
        tlCityDetails.setupWithViewPager(vpCity)

    }
}