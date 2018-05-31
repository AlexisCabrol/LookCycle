package fr.cabrolalexis.velo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseFragment

class MapFragment: BaseFragment() {

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}