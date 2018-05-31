package fr.cabrolalexis.velo.model

import com.google.gson.annotations.SerializedName

data class Station(
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
        @SerializedName("last_update") var timestamp: Long
)