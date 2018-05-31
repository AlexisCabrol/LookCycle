package fr.cabrolalexis.velo.view.adapter

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.model.City
import kotlinx.android.synthetic.main.item_city.view.*

class CityListAdapter(val cityListAdapterCallback: CityListAdapterCallback) : ListAdapter<City, CityListAdapter.CityViewHolder>(DiffCityCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItemAtPosition(position: Int): City = getItem(position)

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var item: City

        fun bind(item: City) {
            this.item = item
            loadTexts(item)
            loadActions()
        }

        fun onItemClick() {
            cityListAdapterCallback.onItemClick(item)
        }

        private fun loadActions() {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .subscribe({ onItemClick() })
        }

        private fun loadTexts(item: City) {
            itemView.nameCity.text = item.name
            itemView.nameCommercial.text = item.commercial_name
        }
    }

    interface CityListAdapterCallback {
        fun onItemClick(city: City)
    }
}

class DiffCityCallback: DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City?, newItem: City?): Boolean = oldItem?.name.equals(newItem?.name)

    override fun areContentsTheSame(oldItem: City?, newItem: City?): Boolean = oldItem?.commercial_name.equals(newItem?.commercial_name)

}

class ItemDecoration(divider : Drawable) : RecyclerView.ItemDecoration() {
    private val divider : Drawable = divider

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight

        for(i in 0 until parent.childCount-1) {
            val item: View = parent.getChildAt(i)
            val top: Int = item.bottom + (item.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val bottom: Int = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }

    }
}