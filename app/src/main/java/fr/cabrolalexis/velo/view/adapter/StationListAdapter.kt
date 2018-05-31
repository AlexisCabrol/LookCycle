package fr.cabrolalexis.velo.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.model.Station
import kotlinx.android.synthetic.main.item_station.view.*

class StationListAdapter(val stationListAdapterCallback: StationListAdapterCallback) : ListAdapter<Station, StationListAdapter.StationViewHolder>(DiffStationCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItemAtPosition(position: Int): Station = getItem(position)

    inner class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var item: Station

        fun bind(item: Station) {
            this.item = item
            loadTexts(item)
            loadActions()
        }

        fun onItemClick() {
            stationListAdapterCallback.onItemClick(item)
        }

        private fun loadActions() {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .subscribe({ onItemClick() })
        }

        private fun loadTexts(item: Station) {
            itemView.nameStation.text = item.name
            itemView.address.text = item.address
            itemView.bikeAvailable.text = itemView.resources.getString(R.string.bikeAvailable,item.veloAvailable.toString())
            itemView.standsAvailable.text = itemView.resources.getString(R.string.standsAvailable,item.emptyStands.toString())
        }
    }

    interface StationListAdapterCallback {
        fun onItemClick(station: Station)
    }
}

class DiffStationCallback: DiffUtil.ItemCallback<Station>() {
    override fun areItemsTheSame(oldItem: Station?, newItem: Station?): Boolean = oldItem?.number == newItem?.number

    override fun areContentsTheSame(oldItem: Station?, newItem: Station?): Boolean = oldItem?.name.equals(newItem?.name)

}

