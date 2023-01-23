package com.sogya.data.mappers.user

import com.sogya.data.models.UserData
import com.sogya.domain.models.UserDomain

class UserDomainMapper(
    private val user: UserDomain
) {
    fun toUserData() = UserData(
        user.id,
        user.email,
        user.name,
        user.serverUriList
    )
}