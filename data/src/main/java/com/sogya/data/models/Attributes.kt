package com.sogya.data.models

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Attributes(

    @SerializedName("entity_id")
    var entityId: ArrayList<String>? = null,

    //    public String entityId;
    @SerializedName("next_dawn")
    var nextDawn: String? = null,

    @SerializedName("next_dusk")
    var nextDusk: String? = null,

    @SerializedName("next_midnight")
    var nextMidnight: String? = null,

    @SerializedName("next_noon")
    var nextNoon: String? = null,

    @SerializedName("next_rising")
    var nextRising: String? = null,

    @SerializedName("next_setting")
    var nextSetting: String? = null,

    @SerializedName("speed_list")
    var speedList: ArrayList<String>? = null,

    @SerializedName("fan_speed_list")
    var fanSpeedList: ArrayList<String>? = null,

    @SerializedName("assumed_state")
    var assumedState: String? = null,

    @SerializedName("friendly_name")
    var friendlyName: String? = null,

    @SerializedName("app_name")
    var appName: String? = null,

    @SerializedName("is_volume_muted")
    var isVolumeMuted: Boolean? = null,

    @SerializedName("volume_level")
    var volumeLevel: BigDecimal? = null,

    @SerializedName("entity_picture")
    var entityPicture: String? = null,

    @SerializedName("icon")
    var icon: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("year")
    var year: Int? = null,

    @SerializedName("month")
    var month: Int? = null,

    @SerializedName("day")
    var day: Int? = null,

    @SerializedName("hour")
    var hour: Int? = null,

    @SerializedName("minute")
    var minute: Int? = null,

    @SerializedName("timestamp")
    var timestamp: String? = null,

    @SerializedName("has_date")
    var hasDate: Boolean? = null,

    @SerializedName("has_time")
    var hasTime: Boolean? = null,

    @SerializedName("device_class")
    var deviceClass: String? = null,

    @SerializedName("brightness")
    var brightness: BigDecimal? = null,

    @SerializedName("color_temp")
    var colorTemp: BigDecimal? = null,

    @SerializedName("rgb_color")
    var rgbColors: ArrayList<BigDecimal>? = null,

    @SerializedName("options")
    var options: ArrayList<String>? = null,

    @SerializedName("order")
    var order: Int? = null,

    @SerializedName("auto")
    var auto: Boolean? = null,

    @SerializedName("hidden")
    var hidden: Boolean? = null,

    @SerializedName("view")
    var view: Boolean? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("initial")
    var initial: Int? = null,

    @SerializedName("code_format")
    var codeFormat: String? = null,

    @SerializedName("pattern")
    var pattern: String? = null,

    @SerializedName("max")
    var max: BigDecimal? = null,

    @SerializedName("min")
    var min: BigDecimal? = null,

    @SerializedName("step")
    var step: BigDecimal? = null,

    @SerializedName("current_temperature")
    var currentTemperature: BigDecimal? = null,

    @SerializedName("max_temp")
    var maxTemp: BigDecimal? = null,

    @SerializedName("min_temp")
    var minTemp: BigDecimal? = null,

    @SerializedName("temperature")
    var temperature: String? = null,

    @SerializedName("operation_mode")
    var operationMode: String? = null,

    @SerializedName("activity")
    var activity: String? = null,

    @SerializedName("provider")
    var provider: String? = null,

    @SerializedName(
        "source_type"
    )
    var sourceType: String? = null,

    //Can be "High"(String) or Number
    @SerializedName("battery")
    var battery: String? = null,

    @SerializedName("gps_accuracy")
    var gpsAccuracy: BigDecimal? = null,

    @SerializedName("altitude")
    var altitude: BigDecimal? = null,

    @SerializedName("latitude")
    var latitude: BigDecimal? = null,

    @SerializedName("longitude")
    var longitude: BigDecimal? = null,

    @SerializedName("radius")
    var radius: BigDecimal? = null,

    //https://home-assistant.io/blog/2017/08/12/release-51/#release-0512---august-14
    @SerializedName("release_notes")
    var releaseNotes: Boolean? = null,

    @SerializedName("unit_of_measurement")
    var unitOfMeasurement: String? = null,
) {
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

    fun getTemperature(): BigDecimal? {
        //Crashlytics.log("temperature: $temperature")
        return if (temperature == null) {
            null
        } else BigDecimal(temperature)
    }

    fun isView(): Boolean {
        return view != null && view as Boolean
    }
}