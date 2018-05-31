package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.adapter.CityDetailsViewPagerAdapter
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.view.fragment.MapFragment
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
        viewModel.fetchStation(name)
    }

    private fun setupViewPager() {
        val adapter = CityDetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MapFragment.newInstance(), getString(R.string.titleMap))
        adapter.addFragment(StationListFragment.newInstance(), getString(R.string.titleStationList))
        vpCity.adapter = adapter
        tlCityDetails.setupWithViewPager(vpCity)

    }
}