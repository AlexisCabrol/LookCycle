package fr.cabrolalexis.velo.model

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("name") var name: String,
        @SerializedName("commercial_name") var commercial_name: String
)