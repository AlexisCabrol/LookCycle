package fr.cabrolalexis.velo.model.typeadapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import fr.cabrolalexis.velo.model.Position
import fr.cabrolalexis.velo.model.Station
import java.lang.reflect.Type

class StationTypeAdapter: JsonDeserializer<Station> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Station {
        var position: Position = if(json.asJsonObject.has("position")) {
            Position(json.asJsonObject.get("position").asJsonObject.get("lat").asDouble,json.asJsonObject.get("position").asJsonObject.get("lng").asDouble)
        } else {
            Position(0.toDouble(),0.toDouble())
        }

        return Station(
                json.asJsonObject.get("number").asInt,
                json.asJsonObject.get("contract_name").asString,
                json.asJsonObject.get("name").asString,
                json.asJsonObject.get("address").asString,
                position,
                json.asJsonObject.get("banking").asBoolean,
                json.asJsonObject.get("bonus").asBoolean,
                json.asJsonObject.get("status").asString,
                json.asJsonObject.get("bike_stands").asInt,
                json.asJsonObject.get("available_bike_stands").asInt,
                json.asJsonObject.get("available_bikes").asInt,
                json.asJsonObject.get("last_update").asLong
        )
    }

}