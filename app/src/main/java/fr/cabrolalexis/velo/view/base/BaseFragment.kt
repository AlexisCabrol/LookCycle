package fr.cabrolalexis.velo.view.base

import android.support.v4.app.Fragment
import fr.cabrolalexis.velo.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

open class BaseFragment: Fragment(), KodeinAware {
    val rxDelegate = RxLifecycleDelegate()
    override val kodein by closestKodein()

    override fun onPause() {
        super.onPause()
        rxDelegate.onFragmentPause()
    }

    override fun onStop() {
        super.onStop()
        rxDelegate.onFragmentStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rxDelegate.onFragmentDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDelegate.onFragmentDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        rxDelegate.onFragmentDetach()
    }

    protected fun lifecycle(event: RxLifecycleDelegate.FragmentEvent): Observable<RxLifecycleDelegate.FragmentEvent> {
        return rxDelegate.lifecycle(event)
    }

}