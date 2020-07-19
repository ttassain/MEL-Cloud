package com.deuttai.clim

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

/**
 * @param language Language (0 à 25, 0=English, 4=Deutsch, 6=Español, 7=Français, 19=Italiano) détermine entre autre l'unité pour la température (Celsius ou Fahrenheit)
 */
data class LoginDetails(
    @JsonProperty("AppVersion") var appVersion: String,
    @JsonProperty("Language") var language: Int,
    @JsonProperty("Persist") var persist: Boolean,
    @JsonProperty("Email") var email: String,
    @JsonProperty("Password") var password: String,
    @JsonProperty("CaptchaChallenge") var captchaChallenge: String? = null,
    @JsonProperty("CaptchaResponse") var captchaResponse: String? = null
)

data class LoginInfos(
    @JsonProperty("ErrorId") val errorId: Int,
    @JsonProperty("ErrorMessage") val errorMessage: String?,
    @JsonProperty("LoginData") val loginData: LoginData
)

data class LoginData(
    @JsonProperty("ContextKey") val contextKey: String,
    @JsonProperty("Name") val name: String,
    @JsonProperty("Duration") val duration: Long,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty("Expiry") val expiry: Date,
    @JsonProperty("CountryName") val countryName: String,
    @JsonProperty("MapZoom") val mapZoom: Int,
    @JsonProperty("MapLongitude") val mapLongitude: Double,
    @JsonProperty("MapLatitude") val mapLatitude: Double
)

data class BuildingInfos(
    @JsonProperty("ID") val id: Int,
    @JsonProperty("Name") val name: String,
    @JsonProperty("AddressLine1") val addressLine1: String,
    @JsonProperty("AddressLine2") val addressLine2: String,
    @JsonProperty("City") val city: String,
    @JsonProperty("Postcode") val postcode: String,
    @JsonProperty("Latitude") val latitude: Double,
    @JsonProperty("Longitude") val longitude: Double,
    @JsonProperty("Structure") val structure: StructureInfo
)

data class StructureInfo(
    @JsonProperty("Devices") val devices: List<DeviceInfo>
)

data class DeviceInfo(
    @JsonProperty("DeviceID") val id: Int,
    @JsonProperty("DeviceName") val name: String,
    @JsonProperty("BuildingID") val buildingId: Int,
    @JsonProperty("MacAddress") val macAddress: String,
    @JsonProperty("SerialNumber") val serialNumber: String,
    @JsonProperty("Permissions") val permissions: PermissionsInfo
)

data class PermissionsInfo(
    @JsonProperty("CanSetOperationMode") val canSetOperationMode: Boolean,
    @JsonProperty("CanSetFanSpeedval") val canSetFanSpeedval : Boolean,
    @JsonProperty("CanSetVaneDirection") val canSetVaneDirection: Boolean,
    @JsonProperty("CanSetPowerval") val canSetPowerval : Boolean,
    @JsonProperty("CanSetTemperatureIncrementOverride") val canSetTemperatureIncrementOverride: Boolean,
    @JsonProperty("CanDisableLocalController") val canDisableLocalController: Boolean
)

/**
 * @param deviceType Type de device 0=air/air 1=air/eau
 * @param fanSpeed Vitesse du ventilateur de 1 à [numberOfFanSpeeds] (0 = automatique)
 */
data class DeviceStatus(
    @JsonProperty("DeviceID") val deviceId: Int,
    @JsonProperty("DeviceType") val deviceType: Int,
    @JsonProperty("EffectiveFlags") var effectiveFlags: Int,
    @JsonProperty("Power") var power: Boolean,
    @JsonProperty("Offline") val offline: Boolean,
    @JsonProperty("HasPendingCommand") var hasPendingCommand: Boolean,
    @JsonProperty("RoomTemperature") val roomTemperature: Double,
    @JsonProperty("DefaultHeatingSetTemperature") val defaultHeatingSetTemperature: Double,
    @JsonProperty("DefaultCoolingSetTemperature") val defaultCoolingSetTemperature: Double,
    @JsonProperty("ErrorMessage") val errorMessage: String?,
    @JsonProperty("ErrorCode") val errorCode: Int,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty("LastCommunication") val lastCommunication: Date,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty("NextCommunication") val nextCommunication: Date,
    @JsonProperty("SetTemperature") var setTemperature: Double,
    @JsonProperty("SetFanSpeed") var setFanSpeed: Int,
    @JsonProperty("VaneHorizontal") var vaneHorizontal: Int,
    @JsonProperty("VaneVertical") var vaneVertical: Int,
    @JsonProperty("NumberOfFanSpeeds") val numberOfFanSpeeds: Int,
    @JsonProperty("OperationMode") var operationMode: OperationMode
)

enum class OperationMode {
    OFF,
    HEATING,
    DEHUMIDIFIER,
    COOLING,
    DEFROST,
    STANDBY,
    LEGIONELLA,
    VENTILATION,
    AUTOMATIC;

    @JsonValue
    fun toValue() = ordinal
}

enum class Language(val code: Int, val country: String) {
    EN(0, "English"),
    BG(1, "Bulgarian"),
    CS(2, "Czech"),
    DA(3, "Danish"),
    DE(4, "German"),
    ET(5, "Estonian"),
    ES(6, "Spanish"),
    FR(7, "French"),
    HY(8, "Armenian"),
    LV(9, "Latvian"),
    LT(10, "Lithuanian"),
    HU(11, "Hungarian"),
    NL(12, "Dutch"),
    NO(13, "Norwegian"),
    PL(14, "Polish"),
    PT(15, "Portuguese"),
    RU(16, "Russian"),
    FI(17, "Finnish"),
    SV(18, "Swedish"),
    IT(19, "Italian"),
    UK(20, "Ukrainian"),
    TR(21, "Turkish"),
    EL(22, "Greek"),
    HR(23, "Croatian"),
    RO(24, "Romanian"),
    SL(25, "Slovenian")
}

object EffectiveFlag {
    const val POWER = 1
    const val OPERATION_MODE = 2
    const val DESIRED_TEMPERATURE = 4
    const val FAN_SPEED = 8
    const val VANES_VERTICAL = 16
    const val VANES_HORIZONTAL = 256
}
