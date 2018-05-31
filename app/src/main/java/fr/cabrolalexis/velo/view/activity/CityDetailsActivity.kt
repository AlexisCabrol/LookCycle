package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.adapter.CityDetailsViewPagerAdapter
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.view.fragment.MapFragment
import fr.cabrolalexis.velo.view.fragment.StationListFragment
import kotlinx.android.synthetic.main.activity_city_details.*

class CityDetailsActivity: BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, CityDetailsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = CityDetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MapFragment.newInstance(), getString(R.string.titleMap))
        adapter.addFragment(StationListFragment.newInstance(), getString(R.string.titleStationList))
        vpCity.adapter = adapter
        tlCityDetails.setupWithViewPager(vpCity)

    }
}