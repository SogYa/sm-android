package com.sogya.domain.models

data class IntegrationResponseDomain(
    val cloudHookUrl:String,
    val remoteUiUrl:String,
    val token:String,
    val webhookId:String
)