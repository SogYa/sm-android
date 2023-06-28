package com.sogya.domain.models

import java.math.BigDecimal

interface AttributesDomain {
    //    public String entityId;
    var nextDawn: String?
    var nextDusk: String?
    var nextMidnight: String?
    var nextNoon: String?
    var nextRising: String?
    var nextSetting: String?
    var speedList: ArrayList<String>?
    var fanSpeedList: ArrayList<String>?
    var assumedState: String?
    var friendlyName: String?
    var appName: String?
    var isVolumeMuted: Boolean?
    var volumeLevel: Double?
    var entityPicture: String?
    var icon: String?
    var title: String?
    var year: Int?
    var month: Int?
    var day: Int?
    var hour: Int?
    var minute: Int?
    var timestamp: String?
    var hasDate: Boolean?
    var hasTime: Boolean?
    var deviceClass: String?
    var brightness: BigDecimal?
    var colorTemp: BigDecimal?
    var rgbColors: ArrayList<BigDecimal>?
    var options: ArrayList<String>?
    var order: Int?
    var auto: Boolean?
    var hidden: Boolean?
    var view: Boolean?
    var name: String?
    var initial: Int?
    var codeFormat: String?
    var pattern: String?
    var max: BigDecimal?
    var min: BigDecimal?
    var step: BigDecimal?
    var currentTemperature: BigDecimal?
    var maxTemp: BigDecimal?
    var minTemp: BigDecimal?
    var temperature: String?
    var operationMode: String?
    var activity: String?
    var provider: String?
    var sourceType: String?

    //Can be "High"(String) or Number
    var battery: String?
    var gpsAccuracy: BigDecimal?
    var altitude: BigDecimal?
    var latitude: BigDecimal?
    var longitude: BigDecimal?
    var radius: BigDecimal?

    //https://home-assistant.io/blog/2017/08/12/release-51/#release-0512---august-14
    var releaseNotes: Boolean?
    var unitOfMeasurement: String?
    var mediaPlayerSongName: String?
    var mediaPlayerArtistName: String?
    var mediaDuration: Double?
    var mediaPosition: Double?
    var currentPosition: Int?
}