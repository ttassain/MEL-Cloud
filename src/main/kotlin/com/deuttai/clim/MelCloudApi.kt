package com.deuttai.clim

import feign.Headers
import feign.Param
import feign.RequestLine

interface MelCloudApi {

    @RequestLine("POST /Mitsubishi.Wifi.Client/Login/ClientLogin")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun login(
        loginDetails: LoginDetails
    ): LoginInfos

    // TODO : login auto a tester
    // /Mitsubishi.Wifi.Client/Login/ClientSavedLogin/?key=XXXXX&appVersion=XXXXX

    // TODO : requete pour un DeviceType = 1
    // /Mitsubishi.Wifi.Client/Device/SetAtw

    @RequestLine("GET /Mitsubishi.Wifi.Client/User/ListDevices")
    @Headers("Content-Type: application/json", "Accept: application/json", "X-MitsContextKey: {contextKey}")
    fun getDevicesInfos(
        @Param("contextKey") contextKey: String
    ): List<BuildingInfos>

    @RequestLine("GET /Mitsubishi.Wifi.Client/Device/Get?id={id}&buildingID={buildingId}")
    @Headers("Content-Type: application/json", "Accept: application/json", "X-MitsContextKey: {contextKey}")
    fun getDeviceStatus(
        @Param("contextKey") contextKey: String,
        @Param("id") deviceId: Int,
        @Param("buildingId") buildingId: Int
    ): DeviceStatus

    @RequestLine("POST/Mitsubishi.Wifi.Client/Device/SetAta")
    @Headers("Content-Type: application/json", "Accept: application/json", "X-MitsContextKey: {contextKey}")
    fun setDeviceStatus(
        @Param("contextKey") contextKey: String,
        deviceStatus: DeviceStatus)
}
