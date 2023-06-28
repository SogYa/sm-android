package com.sogya.data.models

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.AttributesDomain
import java.math.BigDecimal

data class AttributesData(
    //    public String entityId;
    @SerializedName("next_dawn")
    override var nextDawn: String? = null,
    @SerializedName("next_dusk")
    override var nextDusk: String? = null,
    @SerializedName("next_midnight")
    override var nextMidnight: String? = null,
    @SerializedName("next_noon")
    override var nextNoon: String? = null,
    @SerializedName("next_rising")
    override var nextRising: String? = null,
    @SerializedName("next_setting")
    override var nextSetting: String? = null,
    @SerializedName("speed_list")
    override var speedList: ArrayList<String>? = null,
    @SerializedName("fan_speed_list")
    override var fanSpeedList: ArrayList<String>? = null,
    @SerializedName("assumed_state")
    override var assumedState: String? = null,
    @SerializedName("friendly_name")
    override var friendlyName: String? = null,
    @SerializedName("app_name")
    override var appName: String? = null,
    @SerializedName("is_volume_muted")
    override var isVolumeMuted: Boolean? = null,
    @SerializedName("volume_level")
    override var volumeLevel: Double? = null,
    @SerializedName("entity_picture")
    override var entityPicture: String? = null,
    @SerializedName("icon")
    override var icon: String? = null,
    @SerializedName("title")
    override var title: String? = null,
    @SerializedName("year")
    override var year: Int? = null,
    @SerializedName("month")
    override var month: Int? = null,
    @SerializedName("day")
    override var day: Int? = null,
    @SerializedName("hour")
    override var hour: Int? = null,
    @SerializedName("minute")
    override var minute: Int? = null,
    @SerializedName("timestamp")
    override var timestamp: String? = null,
    @SerializedName("has_date")
    override var hasDate: Boolean? = null,
    @SerializedName("has_time")
    override var hasTime: Boolean? = null,
    @SerializedName("device_class")
    override var deviceClass: String? = null,
    @SerializedName("brightness")
    override var brightness: BigDecimal? = null,
    @SerializedName("color_temp")
    override var colorTemp: BigDecimal? = null,
    @SerializedName("rgb_color")
    override var rgbColors: ArrayList<BigDecimal>? = null,
    @SerializedName("options")
    override var options: ArrayList<String>? = null,
    @SerializedName("order")
    override var order: Int? = null,
    @SerializedName("auto")
    override var auto: Boolean? = null,
    @SerializedName("hidden")
    override var hidden: Boolean? = null,
    @SerializedName("view")
    override var view: Boolean? = null,
    @SerializedName("name")
    override var name: String? = null,
    @SerializedName("initial")
    override var initial: Int? = null,
    @SerializedName("code_format")
    override var codeFormat: String? = null,
    @SerializedName("pattern")
    override var pattern: String? = null,
    @SerializedName("max")
    override var max: BigDecimal? = null,
    @SerializedName("min")
    override var min: BigDecimal? = null,
    @SerializedName("step")
    override var step: BigDecimal? = null,
    @SerializedName("current_temperature")
    override var currentTemperature: BigDecimal? = null,
    @SerializedName("max_temp")
    override var maxTemp: BigDecimal? = null,
    @SerializedName("min_temp")
    override var minTemp: BigDecimal? = null,
    @SerializedName("temperature")
    override var temperature: String? = null,
    @SerializedName("operation_mode")
    override var operationMode: String? = null,
    @SerializedName("activity")
    override var activity: String? = null,
    @SerializedName("provider")
    override var provider: String? = null,
    @SerializedName(
        "source_type"
    )
    override var sourceType: String? = null,
    //Can be "High"(String) or Number
    @SerializedName("battery")
    override var battery: String? = null,
    @SerializedName("gps_accuracy")
    override var gpsAccuracy: BigDecimal? = null,
    @SerializedName("altitude")
    override var altitude: BigDecimal? = null,
    @SerializedName("latitude")
    override var latitude: BigDecimal? = null,
    @SerializedName("longitude")
    override var longitude: BigDecimal? = null,
    @SerializedName("radius")
    override var radius: BigDecimal? = null,
    //https://home-assistant.io/blog/2017/08/12/release-51/#release-0512---august-14
    @SerializedName("release_notes")
    override var releaseNotes: Boolean? = null,
    @SerializedName("unit_of_measurement")
    override var unitOfMeasurement: String? = null,
    //Media player
    @SerializedName("media_title")
    override var mediaPlayerSongName: String? = null,
    @SerializedName("media_artist")
    override var mediaPlayerArtistName: String? = null,
    @SerializedName("media_duration")
    override var mediaDuration: Double? = 0.0,
    @SerializedName("media_position")
    override var mediaPosition: Double? = 0.0,
    //Cover
    @SerializedName("current_position")
    override var currentPosition: Int? = 0
) : AttributesDomain {
    val numberOfDecimalPlaces: Int
        get() {
            val string = step!!.stripTrailingZeros().toPlainString()
            val index = string.indexOf(".")
            return if (index < 0) 0 else string.length - index - 1
        }
    val entityPictureUri: Uri
        get() = Uri.parse(if (entityPicture!!.startsWith("//")) "http:$entityPicture" else entityPicture)
    val timestampForInputDateTime: Long?
        get() = if (timestamp == null) null else BigDecimal(timestamp).toLong()

    fun getTemp(): BigDecimal? {
        //Crashlytics.log("temperature: $temperature")
        return if (temperature == null) {
            null
        } else BigDecimal(temperature)
    }

    fun isView(): Boolean {
        return view != null && view as Boolean
    }
}