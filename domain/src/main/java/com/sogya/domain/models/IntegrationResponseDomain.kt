package com.sogya.domain.models

interface IntegrationResponseDomain {
    val cloudHookUrl: String
    val remoteUiUrl: String
    val token: String
    val webhookId: String
}
