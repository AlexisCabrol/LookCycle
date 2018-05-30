package fr.cabrolalexis.velo.view.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.model.City
import kotlinx.android.synthetic.main.item_city.view.*

class CityListAdapter : ListAdapter<City, CityListAdapter.CityViewHolder>(DiffCityCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItemAtPosition(position: Int): City = getItem(position)

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: City) {
            loadTexts(item)
        }

        private fun loadTexts(item: City) {
            itemView.nameCity.text = item.name
            itemView.nameCommercial.text = item.commercial_name
        }
    }
}

class DiffCityCallback: DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City?, newItem: City?): Boolean = oldItem?.name.equals(newItem?.name)

    override fun areContentsTheSame(oldItem: City?, newItem: City?): Boolean = oldItem?.commercial_name.equals(newItem?.commercial_name)

}