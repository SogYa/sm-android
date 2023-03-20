package com.sogya.domain.models

import java.math.BigDecimal
data class AttributesDomain(


//    public String entityId;

var nextDawn: String? = null,


var nextDusk: String? = null,


var nextMidnight: String? = null,

var nextNoon: String? = null,

var nextRising: String? = null,

var nextSetting: String? = null,

var speedList: ArrayList<String>? = null,

var fanSpeedList: ArrayList<String>? = null,

var assumedState: String? = null,

var friendlyName: String? = null,

var appName: String? = null,

var isVolumeMuted: Boolean? = null,

var volumeLevel: Double? = null,

var entityPicture: String? = null,

var icon: String? = null,

var title: String? = null,

var year: Int? = null,

var month: Int? = null,

var day: Int? = null,

var hour: Int? = null,

var minute: Int? = null,

var timestamp: String? = null,

var hasDate: Boolean? = null,

var hasTime: Boolean? = null,

var deviceClass: String? = null,

var brightness: BigDecimal? = null,

var colorTemp: BigDecimal? = null,

var rgbColors: ArrayList<BigDecimal>? = null,

var options: ArrayList<String>? = null,

var order: Int? = null,

var auto: Boolean? = null,

var hidden: Boolean? = null,

var view: Boolean? = null,

var name: String? = null,

var initial: Int? = null,

var codeFormat: String? = null,

var pattern: String? = null,

var max: BigDecimal? = null,

var min: BigDecimal? = null,

var step: BigDecimal? = null,

var currentTemperature: BigDecimal? = null,

var maxTemp: BigDecimal? = null,

var minTemp: BigDecimal? = null,

var temperature: String? = null,

var operationMode: String? = null,

var activity: String? = null,

var provider: String? = null,

var sourceType: String? = null,

//Can be "High"(String) or Number
var battery: String? = null,

var gpsAccuracy: BigDecimal? = null,

var altitude: BigDecimal? = null,

var latitude: BigDecimal? = null,

var longitude: BigDecimal? = null,

var radius: BigDecimal? = null,

//https://home-assistant.io/blog/2017/08/12/release-51/#release-0512---august-14
var releaseNotes: Boolean? = null,

var unitOfMeasurement: String? = null,

var mediaPlayerSongName: String? = null,

var mediaPlayerArtistName: String? = null,

var mediaDuration: Double? = null,

var mediaPosition: Double? = null,

var currentPosition: Int? = null
)