package com.sogya.data.mappers.user

import com.sogya.data.models.UserData
import com.sogya.domain.models.UserDomain

class UserDataMapper(
    private val user: UserData
) {
    fun toUserDomain() = UserDomain(
        user.id,
        user.email,
        user.name,
        user.serverUriList
    )
}