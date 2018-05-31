package fr.cabrolalexis.velo.view.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.data.NetworkEvent
import fr.cabrolalexis.velo.model.Station
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import fr.cabrolalexis.velo.view.adapter.ItemDecoration
import fr.cabrolalexis.velo.view.adapter.StationListAdapter
import fr.cabrolalexis.velo.view.base.BaseFragment
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.fragment_stationlist.*
import org.kodein.di.direct
import org.kodein.di.generic.instance
import timber.log.Timber

class StationListFragment : BaseFragment(), StationListAdapter.StationListAdapterCallback {

    private lateinit var viewModel: CityDetailsViewModel
    private val adapter = StationListAdapter(this)

    companion object {
        fun newInstance(): StationListFragment = StationListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stationlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = kodein.direct.instance(arg = activity)

        rvDetailsList.adapter = adapter
        rvDetailsList.layoutManager = LinearLayoutManager(activity)
        rvDetailsList.addItemDecoration(ItemDecoration(ContextCompat.getDrawable(context!!, R.drawable.item_decoration)!!))
        viewModel.station
                .takeUntil(lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY_VIEW))
                .subscribe({ setupListStation(it) }, { Timber.e(it) })

        viewModel.refreshState
                .takeUntil(lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY_VIEW))
                .subscribe({ onRefreshStateChange(it) }, { Timber.e(it) })

        swipeRefresh.setOnRefreshListener { viewModel.refresh() }

    }

    private fun setupListStation(listStation: List<Station>) {
        if (!listStation.isEmpty()) {
            adapter.submitList(listStation)
        }
    }

    //region network refresh list

    private fun onRefreshStateChange(netStatus: NetworkEvent) {
        when(netStatus) {
            NetworkEvent.None -> {
               onRefreshStateNone()
            }
            NetworkEvent.InProgress -> {
               onRefreshStateProgress()
            }
            is NetworkEvent.Error -> {
                onRefreshStateError()
            }
            NetworkEvent.Success -> {
                onRefreshStateSuccess()
            }
        }
    }

    private fun onRefreshStateNone() {
        swipeRefresh.isRefreshing = false
    }

    private fun onRefreshStateProgress() {
        swipeRefresh.isRefreshing = true
    }

    private fun onRefreshStateError() {
        swipeRefresh.isRefreshing = false
        Snackbar.make(clStationList, R.string.refreshfailed , Snackbar.LENGTH_SHORT).show()
    }

    private fun onRefreshStateSuccess() {
        swipeRefresh.isRefreshing = false
        Snackbar.make(clStationList, R.string.refreshsuccess , Snackbar.LENGTH_SHORT).show()
    }

    //endregion

    //region callback list

    override fun onItemClick(station: Station) {
        //TODO
    }

    //endregion
}