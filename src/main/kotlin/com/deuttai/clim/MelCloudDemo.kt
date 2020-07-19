package com.deuttai.clim

import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder

class MelCloudDemo {

    private var loginData: LoginData? = null

    private val melCloudApi = Feign.builder()
        .decoder(JacksonDecoder())
        .encoder(JacksonEncoder())
        .logLevel(Logger.Level.FULL)
        //.logger(Logger.JavaLogger("MelCloud.Logger").appendToFile("api.log"))
        .logger(Logger.ErrorLogger())
        .target(MelCloudApi::class.java, "https://app.melcloud.com")

    init {
        login()

        val buildingInfos = getDevices()

        buildingInfos?.getOrNull(0)?.structure?.devices?.forEach { device ->
            if (device.name == "Bureau") {
                loginData?.contextKey?.let { contextKey ->
                    val deviceStatus = melCloudApi.getDeviceStatus(contextKey, device.id, device.buildingId)
                    println(deviceStatus)

                    // On allume la clim du Bureau !!
                    // Si plusieurs paramètres modifiés en même temps, il faut ajouter les flags : exemple si on allume et change la vitesse des ventilateurs : EffectiveFlag.POWER + EffectiveFlag.FAN_SPEED
                    deviceStatus.effectiveFlags = EffectiveFlag.DESIRED_TEMPERATURE + EffectiveFlag.POWER + EffectiveFlag.OPERATION_MODE
                    deviceStatus.power = true
                    deviceStatus.operationMode = OperationMode.COOLING
                    deviceStatus.setTemperature = 23.0
                    deviceStatus.hasPendingCommand = true
                    melCloudApi.setDeviceStatus(contextKey, deviceStatus)
                }
            }
        }
    }

    private fun getDevices(): List<BuildingInfos>? {
        loginData?.contextKey?.let { contextKey ->
            val buildingInfos = melCloudApi.getDevicesInfos(contextKey)
            println(buildingInfos)
            return buildingInfos
        }
        return null
    }

    private fun login() {
        val loginDetails = LoginDetails(
            appVersion = "1.20.0.5",
            language = Language.FR.code,
            persist = true,
            email = "mon.email@orange.fr",
            password = "Password"
        )

        val loginInfos = melCloudApi.login(loginDetails)
        println(loginInfos)

        loginData = loginInfos.loginData
    }
}
