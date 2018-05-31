package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.model.City
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.CityListAdapter
import fr.cabrolalexis.velo.view.adapter.ItemDecoration
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.generic.instance

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


        viewModel.city
                 .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                 .subscribe({
                     setupListCity(it)
                 })


    }

    private fun setupListCity(listCity: List<City>) {
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
