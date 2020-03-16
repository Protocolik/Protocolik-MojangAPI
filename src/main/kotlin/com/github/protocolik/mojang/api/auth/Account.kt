package com.github.protocolik.mojang.api.auth

import com.github.protocolik.mojang.api.Profile
import java.util.*

data class Account(
        val profile: Profile,
        val accessToken: UUID
)