package fr.cabrolalexis.velo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseFragment
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import org.kodein.di.direct
import org.kodein.di.generic.instance

class StationListFragment: BaseFragment() {

    private lateinit var viewModel: CityDetailsViewModel

    companion object {
        fun newInstance(): StationListFragment = StationListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stationlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = kodein.direct.instance(arg = this)

    }
}