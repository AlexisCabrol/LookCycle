package fr.cabrolalexis.velo.model

import com.google.gson.annotations.SerializedName

data class Position(
        @SerializedName("lat") var lat: Long,
        @SerializedName("lng") var lng: Long
)