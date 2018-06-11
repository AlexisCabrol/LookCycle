package fr.cabrolalexis.velo.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem


class Station(
        @SerializedName("number") var number: Int,
        @SerializedName("contract_name") var contractName: String,
        @SerializedName("name") var name: String,
        @SerializedName("address") var address: String,
        var position: Position,
        @SerializedName("banking") var banking: Boolean,
        @SerializedName("bonus") var bonus: Boolean,
        @SerializedName("status") var status: String,
        @SerializedName("bike_stands") var capacity: Int,
        @SerializedName("available_bike_stands") var emptyStands: Int,
        @SerializedName("available_bikes") var veloAvailable: Int,
        @SerializedName("last_update") var timestamp: Long) : ClusterItem {

    var info: String? = null

    override fun getSnippet(): String {
        return info!!
    }

    override fun getTitle(): String {
        return name
    }

    override fun getPosition(): LatLng {
        return LatLng(position.lat, position.lng)
    }
}
