package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.model.Station
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.ItemDecoration
import fr.cabrolalexis.velo.view.adapter.StationListAdapter
import fr.cabrolalexis.velo.view.base.BaseActivity
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.activity_search.*
import org.kodein.di.generic.instance
import timber.log.Timber

class SearchActivity : BaseActivity(), StationListAdapter.StationListAdapterCallback {


    private val viewModel: CityDetailsViewModel by instance(arg = this)
    private val adapter = StationListAdapter(this)

    companion object {
        fun createIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvSearch.adapter = adapter
        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.addItemDecoration(ItemDecoration(ContextCompat.getDrawable(this, R.drawable.item_decoration)!!))

        back.clicks()
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ finish() }, { Timber.e(it) })

        search.textChanges()
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({
                    viewModel.searchText.onNext(it.toString().toLowerCase())
                }, { Timber.e(it) })

        viewModel.stationSearch
                .takeUntil(lifecycle(RxLifecycleDelegate.ActivityEvent.DESTROY))
                .subscribe({ setupListSearch(it) }, { Timber.e(it) })
    }

    private fun setupListSearch(listStation: List<Station>) {
        if (!listStation.isEmpty()) {
            noStation.visibility = GONE
            adapter.submitList(listStation)
        } else {
            adapter.submitList(listOf())
            noStation.visibility = VISIBLE
        }
    }


    override fun onItemClick(station: Station) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}