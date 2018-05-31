package fr.cabrolalexis.velo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseFragment

class StationListFragment: BaseFragment() {

    companion object {
        fun newInstance(): StationListFragment = StationListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stationlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}